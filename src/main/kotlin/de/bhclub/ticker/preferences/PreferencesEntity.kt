package de.bhclub.ticker.preferences

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class PreferencesEntity(
    @Id
    val id: String,

    @Column(length = 2000)
    val value: String,
)