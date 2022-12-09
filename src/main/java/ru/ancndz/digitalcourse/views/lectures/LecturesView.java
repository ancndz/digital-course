package ru.ancndz.digitalcourse.views.lectures;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import ru.ancndz.digitalcourse.views.MainLayout;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@PageTitle(LecturesView.PAGE_TITLE)
@Route(value = "lectures", layout = MainLayout.class)
public class LecturesView extends Div implements AfterNavigationObserver {

    public static final String PAGE_TITLE = "Лекции";
    Grid<Lecture> grid = new Grid<>();

    public LecturesView() {
        addClassName("lectures-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(this::createCard);
        add(grid);
    }

    private HorizontalLayout createCard(Lecture lecture) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(lecture.getName());
        name.addClassName("name");
        Span date = new Span(lecture.getDate());
        date.addClassName("date");
        header.add(name, date);

        Span src = new Span(String.format("%s (%d KB)", lecture.getSrc().toString(),
                lecture.getSrc().toFile().length() / 1024));
        src.addClassName("src");


        description.add(header, src, getDownloadButton(lecture));
        card.add(description);
        return card;
    }

    private Component getDownloadButton(Lecture lecture) {
        final StreamResource streamResource = new StreamResource(lecture.getSrc().getFileName().toString(),
                () -> getStream(lecture.getSrc()));
        Anchor link = new Anchor(streamResource, "Скачать");
        link.getElement().setAttribute("download", true);
        return link;
    }
    private InputStream getStream(Path file) {
        final InputStream stream;
        try {
            stream = Files.newInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return stream;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        final List<Lecture> lectures = new ArrayList<>();

        try (var pathStream = Files.walk(Path.of("data", "lectures"), 1)) {
            pathStream.parallel()
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".pdf"))
                    .map(Lecture::new)
                    .forEach(lectures::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        grid.setItems(lectures);
    }
}
