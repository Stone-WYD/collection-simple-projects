package com.wyd.pdf.pdf2excel;


import lombok.Data;

import java.util.Objects;
import java.util.Set;


/*
* 图号实例
* */
@Data
public class DrawInstance {

    private String level;

    private String drawingNumber;

    private String drawingName;

    private String parentDrawingNumber;

    private Set<String> processSequence;

    private String processString;

    private DrawInstance parent;

    private Set<DrawInstance> children;

    private String originalText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawInstance that = (DrawInstance) o;
        return Objects.equals(drawingNumber, that.drawingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawingNumber);
    }

    @Override
    public String toString() {
        return "DrawInstance{" +
                "level='" + level + '\'' +
                ", drawingNumber='" + drawingNumber + '\'' +
                ", parentDrawingNumber='" + parentDrawingNumber + '\'' +
                ", processSequence=" + processSequence +
                ", processString='" + processString + '\'' +
                '}';
    }
}
