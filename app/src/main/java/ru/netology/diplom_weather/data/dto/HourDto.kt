package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class HourDto(
    val condition: ConditionDto,
    val temp_c: Double,
    val time: String,
){

    val date get() = time.toLocalDate("yyyy-MM-dd HH:mm")
}


fun String.toLocalDate(format: String): LocalDateTime {

    val formatter = DateTimeFormatter.ofPattern(format)

    return LocalDateTime.parse(this, formatter)
}
