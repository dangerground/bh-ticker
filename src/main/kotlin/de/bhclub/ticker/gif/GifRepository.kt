package de.bhclub.ticker.gif

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface GifRepository : CrudRepository<Gif, UUID>