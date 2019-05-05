package de.bhclub.ticker.preferences;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class PreferencesEntity {

    @Id
    private String id;

    @Column(length = 2000)
    private String value;
}
