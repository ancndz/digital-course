package ru.ancndz.digitalcourse.views.lectures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Lecture {

    private static final Logger LOG = LoggerFactory.getLogger(Lecture.class);

    private String name;
    private String date;

    private Path src;

    public Lecture(final Path path) {
        this.name = path.getFileName()
                .toString()
                .substring(0, path.getFileName().toString().lastIndexOf("."))
                .replace("_", " ");
        try {
            this.date = Files.readAttributes(path, BasicFileAttributes.class)
                    .lastModifiedTime()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("dd MMMM yyyy hh:mm"));
        } catch (Exception e) {
            LOG.error("Filed to read last modified date from file: " + path);
        }

        this.src = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Path getSrc() {
        return src;
    }

    public void setSrc(Path src) {
        this.src = src;
    }
}
