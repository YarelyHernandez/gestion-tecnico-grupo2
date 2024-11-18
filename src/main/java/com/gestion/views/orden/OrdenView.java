package com.gestion.views.orden;

import com.gestion.controller.EquiposInteractor;
import com.gestion.controller.EquiposInteractorImpl;
import com.gestion.controller.OrdenInteractor;
import com.gestion.controller.OrdenInteractorImpl;
import com.gestion.data.ConsultaEquipos;
import com.gestion.data.Orden;
import com.gestion.data.Tecnico;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PageTitle("Ordenes")
@Route("master-detail2/:ordenID?/:action?(edit)")
@Menu(order = 2, icon = "line-awesome/svg/archive-solid.svg")
public class OrdenView extends Div implements BeforeEnterObserver, OrdenViewModel {

    private final String ORDEN_ID = "ordenID";
    private final String ORDEN_EDIT_ROUTE_TEMPLATE = "master-detail2/%s/edit";

    private final Grid<Orden> grid = new Grid<>(Orden.class, false);

    private TextField id_orden;
    private DatePicker fecha;
    private TextField descripcion;
    private ComboBox<Tecnico> tecnico; // ComboBox para técnicos
    private TextField estado;
    private ComboBox<ConsultaEquipos> equipo; // ComboBox para equipos
    private Integer orden_seleccionadoa_id;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");
    private final Button delete = new Button("Eliminar");

    private Orden orden;

    private OrdenInteractor controlador;
    private List<Tecnico> listaTecnicos = new ArrayList<>(); // Inicializar la lista de técnicos
    private List<ConsultaEquipos> listaEquipos = new ArrayList<>(); // Inicializar la lista de equipos

    public OrdenView() {
        addClassNames("orden-view");

        this.controlador = new OrdenInteractorImpl(this);

        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configurar la tabla (Grid)
        grid.addColumn(Orden::getId_orden).setAutoWidth(true).setHeader("ID");
        grid.addColumn(orden -> {
            // Formatear la fecha a "dd/MM/yyyy"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return orden.getFecha_creacion().formatted(formatter);
        }).setAutoWidth(true).setHeader("Fecha de orden");
        grid.addColumn(Orden::getDescripcion).setAutoWidth(true).setHeader("Descripción");
        grid.addColumn(orden -> {
            // Mostrar el nombre del técnico en lugar del ID
            if (listaTecnicos != null) {
                for (Tecnico tec : listaTecnicos) {
                    if (tec.getId_tecnicos() == Integer.parseInt(orden.getId_tecnicos())) {
                        return tec.getnombre();
                    }
                }
            }
            return ""; // O algún valor por defecto si no se encuentra el técnico
        }).setAutoWidth(true).setHeader("Técnico");
        grid.addColumn(Orden::getEstado).setAutoWidth(true).setHeader("Estado");
        grid.addColumn(orden -> {
            // Mostrar el modelo del equipo en lugar del ID
            if (listaEquipos != null) {
                for (ConsultaEquipos eq : listaEquipos) {
                    if (eq.getId_equipos() == Integer.parseInt(orden.getId_equipos())) {
                        return eq.getModelo();
                    }
                }
            }
            return ""; // O algún valor por defecto si no se encuentra el equipo
        }).setAutoWidth(true).setHeader("Equipo");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                orden_seleccionadoa_id = event.getValue().getId_orden();
                this.orden = new Orden();
                this.orden.setId_orden(event.getValue().getId_orden());
                this.orden.setFecha_creacion(event.getValue().getFecha_creacion());
                this.orden.setDescripcion(event.getValue().getDescripcion());
                this.orden.setId_tecnicos(event.getValue().getId_tecnicos());
                this.orden.setEstado(event.getValue().getEstado());
                this.orden.setId_equipos(event.getValue().getId_equipos());

                this.id_orden.setValue(String.valueOf(this.orden.getId_orden()));
                this.fecha.setValue(LocalDate.parse(this.orden.getFecha_creacion(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                this.descripcion.setValue(this.orden.getDescripcion());

                // Buscar el técnico en la lista y seleccionarlo en el ComboBox
                for (Tecnico tec : listaTecnicos) {
                    if (tec.getId_tecnicos() == Integer.parseInt(this.orden.getId_tecnicos())) {
                        this.tecnico.setValue(tec);
                        break;
                    }
                }

                this.estado.setValue(this.orden.getEstado());

                // Buscar el equipo en la lista y seleccionarlo en el ComboBox
                for (ConsultaEquipos eq : listaEquipos) {
                    if (eq.getId_equipos() == Integer.parseInt(this.orden.getId_equipos())) {
                        this.equipo.setValue(eq);
                        break;
                    }
                }
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
                    this.orden = new Orden();
                    this.orden.setFecha_creacion(this.fecha.getValue().atStartOfDay().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
                    this.orden.setDescripcion(this.descripcion.getValue());
                    this.orden.setId_tecnicos(this.tecnico.getValue().getId_tecnicos().toString()); // Obtener el ID del técnico del ComboBox
                    this.orden.setEstado(this.estado.getValue());
                    this.orden.setId_equipos(this.equipo.getValue().getId_equipos().toString()); // Obtener el ID del equipo del ComboBox

                    this.controlador.crearNuevaOrden(orden);
                } else {
                    this.orden = grid.asSingleSelect().getValue();
                    this.orden.setFecha_creacion(this.fecha.getValue().atStartOfDay().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
                    this.orden.setDescripcion(this.descripcion.getValue());
                    this.orden.setId_tecnicos(this.tecnico.getValue().getId_tecnicos().toString()); // Obtener el ID del técnico del ComboBox
                    this.orden.setEstado(this.estado.getValue());
                    this.orden.setId_equipos(this.equipo.getValue().getId_equipos().toString()); // Obtener el ID del equipo del ComboBox

                    this.controlador.actualizarOrden(orden);
                }

                clearForm();
                refreshGrid();

            } catch (Exception ex) {
                Notification.show("Error al guardar los datos.", 3000, Position.MIDDLE);
            }
        });

        delete.addClickListener(e -> {
            if (this.orden_seleccionadoa_id != null) {
                this.controlador.eliminarOrden(orden_seleccionadoa_id);
                Notification.show("Dato Eliminado");
                clearForm();
                refreshGrid();
            }
        });

        // Cargar datos de técnicos y equipos al iniciar
        this.controlador.consultarTecnicos();
        this.controlador.consultarEquipos();
        this.controlador.consultarOrdenes();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> ordenIdParam = event.getRouteParameters().get(ORDEN_ID);
        if (ordenIdParam.isPresent()) {
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        id_orden = new TextField("ID Orden");
        id_orden.setClearButtonVisible(true);
        id_orden.setPrefixComponent(VaadinIcon.PENCIL.create());
        id_orden.setReadOnly(true);

        fecha = new DatePicker("Fecha");
        fecha.setClearButtonVisible(true);
        fecha.setPrefixComponent(VaadinIcon.CALENDAR.create());

        descripcion = new TextField("Descripción");
        descripcion.setClearButtonVisible(true);
        descripcion.setPrefixComponent(VaadinIcon.PENCIL.create());

        tecnico = new ComboBox<>("Técnico"); // ComboBox para técnicos
        tecnico.setClearButtonVisible(true);
        tecnico.setPrefixComponent(VaadinIcon.USER.create());
        tecnico.setItems(listaTecnicos); // Asignar la lista de técnicos al ComboBox
        tecnico.setItemLabelGenerator(Tecnico::getnombre); // Mostrar el nombre del técnico

        estado = new TextField("Estado");
        estado.setClearButtonVisible(true);
        estado.setPrefixComponent(VaadinIcon.CHECK_CIRCLE_O.create());

        equipo = new ComboBox<>("Equipo"); // ComboBox para equipos
        equipo.setClearButtonVisible(true);
        equipo.setPrefixComponent(VaadinIcon.TOOLS.create());
        equipo.setItems(listaEquipos); // Asignar la lista de equipos al ComboBox
        equipo.setItemLabelGenerator(ConsultaEquipos::getModelo); // Mostrar el modelo del equipo

        formLayout.add(id_orden, fecha, descripcion, tecnico, estado, equipo);

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
        this.controlador.consultarOrdenes();
    }

    private void clearForm() {
        this.id_orden.setValue("");
        this.fecha.setValue(null);
        this.descripcion.setValue("");
        this.tecnico.setValue(null); // Limpiar el ComboBox de técnicos
        this.estado.setValue("");
        this.equipo.setValue(null); // Limpiar el ComboBox de equipos
        this.orden = null;
        this.orden_seleccionadoa_id = null;
    }

    @Override
    public void refrescarGridOrdenes(List<Orden> ordenes) {
        grid.setItems(ordenes);
    }

    @Override
    public void mostrarMensajeCreacion(boolean respuesta) {
        if (respuesta) {
            Notification.show("Orden creada exitosamente!")
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Error al crear la orden.")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    @Override
    public void mostrarMensajeActualizacion(boolean respuesta) {
        if (respuesta) {
            Notification.show("Orden actualizada exitosamente!")
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Error al actualizar la orden.")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    @Override
    public void mostrarMensajeEliminacion(boolean respuesta) {
        if (respuesta) {
            Notification.show("Orden eliminada exitosamente!")
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Error al eliminar la orden.")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        Notification.show(mensaje, 3000, Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    // Métodos para obtener las listas de técnicos y equipos
    public void setListaTecnicos(List<Tecnico> listaTecnicos) {
        this.listaTecnicos = listaTecnicos;
        this.tecnico.setItems(listaTecnicos); // Actualizar el ComboBox de técnicos
    }

    public void setListaEquipos(List<ConsultaEquipos> listaEquipos) {
        this.listaEquipos = listaEquipos;
        this.equipo.setItems(listaEquipos); // Actualizar el ComboBox de equipos
    }
}