module com.forum {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires static lombok;
    requires java.prefs;

    opens com.forum to javafx.graphics;
    opens com.forum.controller to javafx.fxml;
    opens com.forum.model to com.google.gson, javafx.base;

    exports com.forum;
    exports com.forum.controller;
    exports com.forum.model;
    exports com.forum.repository;
    exports com.forum.service;
}