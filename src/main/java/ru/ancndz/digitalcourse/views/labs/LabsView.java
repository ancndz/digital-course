package ru.ancndz.digitalcourse.views.labs;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import ru.ancndz.digitalcourse.views.MainLayout;
import ru.ancndz.digitalcourse.views.lectures.Lecture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@PageTitle(LabsView.PAGE_TITLE)
@Route(value = "labs", layout = MainLayout.class)
public class LabsView extends Main implements HasComponents, HasStyle, AfterNavigationObserver {

    public static final String PAGE_TITLE = "Лабораторные";
    private final OrderedList imageContainer;

    public LabsView() {
        addClassNames("labs-view", MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE,
                Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Лабораторные");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Список заданий, выполняемых на лабораторных занятиях и дома");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);


        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer);
        add(container, imageContainer);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        try (var pathStream = Files.walk(Path.of("data", "labs"), 1)) {
            pathStream.parallel()
                    .filter(Files::isRegularFile)
                    .map(LabsViewCard::new)
                    .forEach(imageContainer::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
