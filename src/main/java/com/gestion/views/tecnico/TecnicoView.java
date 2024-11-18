package com.gestion.views.tecnico;

import com.gestion.controller.TecnicoInteractor;
import com.gestion.controller.TecnicoInteractorImpl;
import com.gestion.data.Tecnico;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@PageTitle("Técnicos")
@Route("master-detail/:tecnicoID?/:action?(edit)")
@Menu(order = 0, icon = "line-awesome/svg/male-solid.svg")
public class TecnicoView extends Div implements BeforeEnterObserver, TecnicoViewModel {

    private final String TECNICO_ID = "tecnicoID";
    private final String TECNICO_EDIT_ROUTE_TEMPLATE = "master-detail/%s/edit";

    private final Grid<Tecnico> grid = new Grid<>(Tecnico.class, false);

    private TextField id_tecnicos;
    private TextField nombre;
    private TextField especializacion;
    private TextField telefono;
    private List<Tecnico> lista_tecnico;
    private Integer tecnico_seleccionado_id;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");
    private final Button delete = new Button("Eliminar");

    private Tecnico tecnico;
    private final TecnicoInteractor controlador;

    public TecnicoView() {
        addClassNames("tecnico-view");

        lista_tecnico = new ArrayList<>();
        this.controlador = new TecnicoInteractorImpl(this);
        
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        grid.addColumn(Tecnico::getId_tecnicos).setAutoWidth(true).setHeader("ID");
        grid.addColumn(Tecnico::getnombre).setAutoWidth(true).setHeader("Nombre");
        grid.addColumn(Tecnico::getEspecializacion).setAutoWidth(true).setHeader("Especialización");
        grid.addColumn(Tecnico::getTelefono).setAutoWidth(true).setHeader("Teléfono");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                tecnico_seleccionado_id = event.getValue().getId_tecnicos();

                this.tecnico = new Tecnico(); 
                this.tecnico.setId_tecnicos(event.getValue().getId_tecnicos());
                this.tecnico.setnombre(event.getValue().getnombre());
                this.tecnico.setEspecializacion(event.getValue().getEspecializacion());
                this.tecnico.setTelefono(event.getValue().getTelefono());

                this.id_tecnicos.setValue(String.valueOf(this.tecnico.getId_tecnicos()));
                this.nombre.setValue(this.tecnico.getnombre());
                this.especializacion.setValue(this.tecnico.getEspecializacion());
                this.telefono.setValue(this.tecnico.getTelefono());
            } else {
                clearForm();
            }
        });
        cancel.addClickListener(e -> {
            clearForm();
            grid.select(null);
        });

        save.addClickListener(e -> {
            try {
                if (grid.asSingleSelect().isEmpty()) {
                    this.tecnico = new Tecnico();
                    this.tecnico.setnombre(this.nombre.getValue());
                    this.tecnico.setEspecializacion(this.especializacion.getValue());
                    this.tecnico.setTelefono(this.telefono.getValue());
                    this.controlador.crearNuevoTecnico(tecnico);
                } else {
                    this.tecnico = grid.asSingleSelect().getValue();

                    this.tecnico.setnombre(this.nombre.getValue()); 
                    this.tecnico.setEspecializacion(this.especializacion.getValue());
                    this.tecnico.setTelefono(this.telefono.getValue());

                    this.controlador.actualizarTecnico(tecnico);
                }

                clearForm();
                refreshGrid();

            } catch (Exception ex) {
                Notification.show("Error al guardar los datos.", 3000, Position.MIDDLE);
            }
        });

        delete.addClickListener(e -> {
            if (this.tecnico_seleccionado_id != null) {
                this.controlador.eliminarTecnico(tecnico_seleccionado_id);
                Notification.show("Dato Eliminado");
                clearForm();
                refreshGrid();
            }
        });

        this.controlador.consultarTecnicos();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> tecnicoIdParam = event.getRouteParameters().get(TECNICO_ID);
        if (tecnicoIdParam.isPresent()) {
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        id_tecnicos = new TextField("ID Técnicos");
        id_tecnicos.setClearButtonVisible(true);
        id_tecnicos.setPrefixComponent(VaadinIcon.PENCIL.create());
        id_tecnicos.setReadOnly(true);

        nombre = new TextField("Nombre");
        nombre.setClearButtonVisible(true);
        nombre.setPrefixComponent(VaadinIcon.PENCIL.create());

        especializacion = new TextField("Especialización");
        especializacion.setClearButtonVisible(true);
        especializacion.setPrefixComponent(VaadinIcon.PENCIL.create());

        telefono = new TextField("Teléfono");
        telefono.setClearButtonVisible(true);
        telefono.setPrefixComponent(VaadinIcon.PHONE.create());

        formLayout.add(id_tecnicos, nombre, especializacion, telefono);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(save, delete, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
        this.controlador.consultarTecnicos();
    }

    private void clearForm() {
        this.id_tecnicos.setValue("");
        this.nombre.setValue("");
        this.especializacion.setValue("");
        this.telefono.setValue("");
        this.tecnico_seleccionado_id = null;
    }

    @Override
    public void refrescarGridTecnicos(List<Tecnico> tecnicos) {
        Collection<Tecnico> items = tecnicos;
        this.lista_tecnico = tecnicos;
        grid.setItems(items);
    }

    @Override
    public void mostrarMensajeCreacion(boolean respuesta) {
        if (respuesta) {
            Notification.show("Técnico creado exitosamente!")
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Error al crear el técnico.")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    @Override
    public void mostrarMensajeActualizacion(boolean respuesta) {
        if (respuesta) {
            Notification.show("Técnico actualizado exitosamente!")
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Error al actualizar el técnico.")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    @Override
    public void mostrarMensajeEliminacion(boolean respuesta) {
        if (respuesta) {
            Notification.show("Técnico eliminado exitosamente!")
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Error al eliminar el técnico.")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        Notification.show(mensaje, 3000, Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}