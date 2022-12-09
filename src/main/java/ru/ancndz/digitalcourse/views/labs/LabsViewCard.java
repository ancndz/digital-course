package ru.ancndz.digitalcourse.views.labs;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.StreamResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LabsViewCard extends ListItem {

    public LabsViewCard(Path path) {
        addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l");

        Span header = new Span();
        header.addClassNames("text-xl", "font-semibold");
        header.setText(path.getFileName().toString().substring(0, path.getFileName().toString().lastIndexOf(".")));

        Span subtitle = new Span();
        subtitle.addClassNames("text-s", "text-secondary");
        subtitle.setText("");

        Paragraph description = new Paragraph("");
        description.addClassName("my-m");

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.getElement().setAttribute("download", true);
        final StreamResource streamResource = new StreamResource(path.getFileName().toString(),
                () -> getStream(path));
        Anchor link = new Anchor(streamResource, "Скачать");
        badge.add(link);

        add(header, subtitle, description, badge);
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
}
