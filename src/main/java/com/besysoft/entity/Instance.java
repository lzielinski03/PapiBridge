package com.besysoft.entity;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by lzielinski on 12/07/2016.
 */
public class Instance {

    public String id;
    public String activitie;
    public String description;
    public int status;
    public StringDate recivedDate;
    public StringDate deadlineDate;

    public Instance(String id, String activitie, String description, int status, XMLGregorianCalendar recivedDate, XMLGregorianCalendar deadlineDate) {
        this.id = id;
        this.activitie = activitie;
        this.description = description;
        this.status = status;
        this.recivedDate = new StringDate(recivedDate);
        this.deadlineDate = new StringDate(deadlineDate);
    }

    private  class StringDate {
        public String anio;
        public String mes;
        public String dia;
        public String hora;
        public String min;

        public StringDate(XMLGregorianCalendar date) {
            this.anio = Integer.toString(date.getYear());
            this.mes = Integer.toString(date.getMonth());
            this.dia = Integer.toString(date.getDay());
            this.hora = Integer.toString(date.getHour());
            this.min = Integer.toString(date.getMinute());
        }
    }

    /*
    public String getAsJson() {
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void printJson() {
        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }*/

    /*
    {"instance": {
		"id"			: "123",
		"activitie"		: "name",
		"description"	: "desc",
		"status"		: "1",
		"recived"		: "date",
		"deadline"		: "date"
	}}
     */
}
