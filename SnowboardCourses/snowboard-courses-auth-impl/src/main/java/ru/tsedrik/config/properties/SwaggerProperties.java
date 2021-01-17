package ru.tsedrik.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Класс общих свойств для Swagger
 */
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /**
     * Название проекта
     */
    private String title;

    /**
     * Описание проекта
     */
    private String description;

    /**
     * Контактное лицо
     */
    private Contact contact = new Contact();

    public SwaggerProperties() {}

    public SwaggerProperties(String title, String description, Contact contact) {
        this.title = title;
        this.description = description;
        this.contact = contact;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contract) {
        this.contact = contract;
    }

    public static class Contact {

        private String name;
        private String mail;
        private String url;

        public Contact(){}

        public Contact(String name, String mail, String url) {
            this.name = name;
            this.mail = mail;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
