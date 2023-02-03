package de.bhclub.ticker.preferences

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class PreferencesEntity(
    @Id
    val id: String,

    @Column(length = 2000)
    val value: String,
)