package de.bhclub.ticker.text

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ScrollingTextRepository : CrudRepository<ScrollingText, UUID>