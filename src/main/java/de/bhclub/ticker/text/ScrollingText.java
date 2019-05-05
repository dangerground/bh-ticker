package de.bhclub.ticker.text;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.awt.Color;

@Entity
@Data
public class ScrollingText {

    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private Color textColor;
    private Color outlineColor;
    private Color background;
    private String font;
    private Integer spacing;
    private Integer speed;
    private Integer startX;
    private Integer startY;

}
