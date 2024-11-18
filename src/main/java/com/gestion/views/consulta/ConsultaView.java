package com.gestion.views.consulta;

import java.util.List;

import com.gestion.controller.EquiposInteractor;
import com.gestion.controller.EquiposInteractorImpl;
import com.gestion.data.ConsultaEquipos;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Consulta de Equipos")
@Route("")
@Menu(order = 1, icon = "line-awesome/svg/check-circle-solid.svg")
public class ConsultaView extends Composite<VerticalLayout> implements EquiposViewModel {

    private final Grid<ConsultaEquipos> grid = new Grid<>(ConsultaEquipos.class, false);
    
    private TextField idEquipoField;
    private TextField modeloField;
    private EmailField tipoEquipoField;
    private TextField marcaField;
    private EquiposInteractor controlador;
    private List<ConsultaEquipos> todosLosEquipos;

    public ConsultaView() {

        this.controlador = new EquiposInteractorImpl(this);

        VerticalLayout mainLayout = getContent();
        mainLayout.setWidth("100%");
        mainLayout.getStyle().set("flex-grow", "1");
        mainLayout.setJustifyContentMode(JustifyContentMode.START);
        mainLayout.setAlignItems(Alignment.CENTER);

        VerticalLayout layoutColumn2 = new VerticalLayout();
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");

        H3 title = new H3("Equipo");
        title.setWidth("100%");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("100%");

        idEquipoField = new TextField("ID del equipo");
        modeloField = new TextField("Modelo");
        tipoEquipoField = new EmailField("Tipo de equipo");
        marcaField = new TextField("Marca");

        formLayout.add(idEquipoField, modeloField, tipoEquipoField, marcaField);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth("100%");
        buttonLayout.getStyle().set("flex-grow", "1");

        Button buscarButton = new Button("Buscar");
        buscarButton.setWidth("min-content");
        buscarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setWidth("min-content");

        buttonLayout.add(buscarButton, cancelarButton);

        mainLayout.add(layoutColumn2);
        layoutColumn2.add(title, formLayout, buttonLayout);

        mainLayout.add(grid);

        grid.addColumn(ConsultaEquipos::getId_equipos).setAutoWidth(true).setHeader("ID");
        grid.addColumn(ConsultaEquipos::getTipo).setAutoWidth(true).setHeader("Tipo");
        grid.addColumn(ConsultaEquipos::getMarca).setAutoWidth(true).setHeader("Marca");
        grid.addColumn(ConsultaEquipos::getModelo).setAutoWidth(true).setHeader("Modelo");

        this.controlador.consultarEquipos();
        
        idEquipoField.addValueChangeListener(event -> actualizarFiltro());
        modeloField.addValueChangeListener(event -> actualizarFiltro());
        tipoEquipoField.addValueChangeListener(event -> actualizarFiltro());
        marcaField.addValueChangeListener(event -> actualizarFiltro());

        buscarButton.addClickListener(event -> actualizarFiltro());
        
        cancelarButton.addClickListener(event -> {
            idEquipoField.clear();
            modeloField.clear();
            tipoEquipoField.clear();
            marcaField.clear();
            actualizarFiltro();
        });
    }

    
    private void actualizarFiltro() {
        List<ConsultaEquipos> equiposFiltrados = todosLosEquipos.stream()
                .filter(equipo -> idEquipoField.getValue().isEmpty() || equipo.getId_equipos().toString().contains(idEquipoField.getValue()))
                .filter(equipo -> modeloField.getValue().isEmpty() || equipo.getModelo().toLowerCase().contains(modeloField.getValue().toLowerCase()))
                .filter(equipo -> tipoEquipoField.getValue().isEmpty() || equipo.getTipo().toLowerCase().contains(tipoEquipoField.getValue().toLowerCase()))
                .filter(equipo -> marcaField.getValue().isEmpty() || equipo.getMarca().toLowerCase().contains(marcaField.getValue().toLowerCase()))
                .toList();
        if (equiposFiltrados.isEmpty()) {
            Notification.show("No se encontraron equipos con los criterios de búsqueda especificados.").addThemeVariants();
        }
        grid.setItems(equiposFiltrados);
    }
    

    @Override
    public void mostrarEquiposEnGrid(List<ConsultaEquipos> list) {
    	 todosLosEquipos = list;
    	grid.setItems(list);
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        Notification.show(mensaje, 3000, Position.MIDDLE)
        .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    @Override
    public void mostrarMensajeExito(String mensaje) {
    }
}