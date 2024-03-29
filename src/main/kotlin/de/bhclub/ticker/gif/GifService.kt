package de.bhclub.ticker.gif

import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.UUID

@Service
class GifService(
    private val gifRepository: GifRepository,
) : KLogging() {

    private val rootLocation =
        if ("root" == System.getProperty("user.name")) Paths.get("/home/pi/gif") else Paths.get("gif")

    fun getAllGifs() = gifRepository.findAll()

    fun getFile(fileId: UUID): Gif? {
        return gifRepository.findById(fileId).get()
    }

    fun uploadFile(file: MultipartFile): Gif {
        val filename = StringUtils.cleanPath(file.originalFilename ?: "")
        try {
            file.inputStream.use { inputStream ->
                if (file.isEmpty) {
                    throw StorageException("Failed to store empty file $filename")
                }
                if (filename.contains("..")) {
                    // This is a security check
                    throw StorageException(
                        "Cannot store file with relative path outside current directory $filename"
                    )
                }
                val target = rootLocation.resolve(filename)
                logger.warn { "build for DB ${System.getProperty("user.name")}" }
                val gif = Gif(
                    data = file.bytes,
                    path = target.toAbsolutePath().toString(),
                    name = file.originalFilename,
                )
                logger.warn { "rootLocation: ${rootLocation.toAbsolutePath()}, target: ${target.toAbsolutePath()}" }
                Files.copy(inputStream, target.toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING)
                logger.warn { "store to DB" }
                return gifRepository.save(gif)
            }
        } catch (e: IOException) {
            throw StorageException("Failed to store file $filename", e)
        }
    }

    val activeGif: String
        get() = "wave.gif"

    fun getFileRef(fileId: UUID): String? {
//        try {
        val gif = gifRepository.findById(fileId)
        //File file = File.createTempFile("gif", gif.get().getName());
        //file.deleteOnExit();
        //return file.getAbsolutePath();
        return gif.map(Gif::path).orElse(null)
        //        } catch (IOException e) {
        // TODO logging
//            e.printStackTrace();
//        }
    }

    fun getGif(gifId: UUID): Gif? {
        val gif = gifRepository.findById(gifId)
        return gif.orElseGet { null }
    }
}