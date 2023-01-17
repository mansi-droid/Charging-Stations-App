package dev.droid.chargingstationsapp

import dev.droid.chargingstationsapp.utils.Converter
import org.junit.Assert
import org.junit.Test

class ConverterUnitTest {
    @Test
    fun test_format_returns_dash_for_blank_string() {
        Assert.assertEquals("-", Converter.format(""))
    }

    @Test
    fun test_format_returns_same_string_for_non_empty_string() {
        Assert.assertEquals("Hello", Converter.format("Hello"))
    }
}