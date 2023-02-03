package de.bhclub.ticker.preferences

import org.springframework.data.repository.CrudRepository

interface PreferencesRepository : CrudRepository<PreferencesEntity, String>