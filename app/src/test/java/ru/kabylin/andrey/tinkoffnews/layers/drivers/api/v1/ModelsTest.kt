package ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1

import com.google.gson.Gson
import org.junit.Test

class ModelsTest {
    @Test
    fun createNewApiResponseFromJson_successfully() {
        val json = """
            {
              "id": "10024",
              "name": "20122017-tinkoff-bank-x-mgu",
              "text": "Тинькофф Банк начинает сотрудничество с кафедрой математических и компьютерных методов анализа мехмата МГУ",
              "publicationDate": {
                "milliseconds": 1513767691000
              },
              "bankInfoTypeId": 2
            }
        """
    }
}