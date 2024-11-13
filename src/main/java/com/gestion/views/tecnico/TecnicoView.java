package com.gestion.views.tecnico;

import com.gestion.data.Tecnico2;
import com.gestion.services.Tecnico2Service;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Tecnico")
@Route("master-detail/:tecnico2ID?/:action?(edit)")
@Menu(order = 0, icon = "line-awesome/svg/male-solid.svg")
public class TecnicoView extends Div implements BeforeEnterObserver {

    private final String TECNICO2_ID = "tecnico2ID";
    private final String TECNICO2_EDIT_ROUTE_TEMPLATE = "master-detail/%s/edit";

    private final Grid<Tecnico2> grid = new Grid<>(Tecnico2.class, false);

    private TextField id_tecnico;
    private TextField nombre;
    private TextField especialidad;
    private TextField telefono;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Tecnico2> binder;

    private Tecnico2 tecnico2;

    private final Tecnico2Service tecnico2Service;

    public TecnicoView(Tecnico2Service tecnico2Service) {
        this.tecnico2Service = tecnico2Service;
        addClassNames("tecnico-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("id_tecnico").setAutoWidth(true);
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("especialidad").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.setItems(query -> tecnico2Service.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(TECNICO2_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(TecnicoView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Tecnico2.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(id_tecnico).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("id_tecnico");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.tecnico2 == null) {
                    this.tecnico2 = new Tecnico2();
                }
                binder.writeBean(this.tecnico2);
                tecnico2Service.update(this.tecnico2);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(TecnicoView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> tecnico2Id = event.getRouteParameters().get(TECNICO2_ID).map(Long::parseLong);
        if (tecnico2Id.isPresent()) {
            Optional<Tecnico2> tecnico2FromBackend = tecnico2Service.get(tecnico2Id.get());
            if (tecnico2FromBackend.isPresent()) {
                populateForm(tecnico2FromBackend.get());
            } else {
                Notification.show(String.format("The requested tecnico2 was not found, ID = %s", tecnico2Id.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(TecnicoView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        id_tecnico = new TextField("Id_tecnico");
        nombre = new TextField("Nombre");
        especialidad = new TextField("Especialidad");
        telefono = new TextField("Telefono");
        formLayout.add(id_tecnico, nombre, especialidad, telefono);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
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
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Tecnico2 value) {
        this.tecnico2 = value;
        binder.readBean(this.tecnico2);

    }
}
