package com.decagon.facilitymanagementapp_group_two.utils

import org.junit.Assert.*
import org.junit.Test

class ValidationKtTest {

    private val testPara1 = "           "
    private val testPara2 = "Kitchen Food"
    private val testPara3 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijkmlnopqrstuvwxyz" +
        "abcdefghijklimopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"
    private val testPara4 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijkmlnopqrstuvwxyz" +
        "abcdefghijklimopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijkmlnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijkmlnopqrstuvwxyz"
    private val textPara5 = "Select a Category..."
    private val textPara6 = "Appliance"
    private val stack = "NODE"
    private val stack1 = "GOLAND"
    private val squad = "DQ200"
    private val squad1 = "SQ003"
    private val phoneNumber1 = "09021"
    private val phoneNumber2 = "08022320201234"
    private val phoneNumber3 = "09022320123"
    @Test
    fun subject_input_validation() {

        assertTrue(subjectValidation(testPara2))
        assertFalse(subjectValidation(testPara1.trim()))
        assertFalse(subjectValidation(testPara3))
    }

    @Test
    fun text_description_input_validation() {
        assertTrue(descriptionValidation(testPara2.trim()))
        assertFalse(descriptionValidation(testPara1.trim()))
        assertTrue(descriptionValidation(testPara3.trim()))
        assertFalse(descriptionValidation(testPara4.trim()))
    }

    @Test
    fun feed_category_validation() {
        assertFalse(feedSelectionValidation(textPara5))
        assertTrue(feedSelectionValidation(textPara6))
    }

    @Test
    fun squad_input_validation() {

        assertTrue(squadInputValidation(squad1))
        assertFalse(squadInputValidation(squad))
    }

    @Test
    fun stack_input_validation() {

        assertFalse(stackValidation(stack1))
        assertTrue(stackValidation(stack))
    }

    @Test
    fun phone_input_validation() {
        assertFalse(phoneNumberValidator(phoneNumber1))
        assertFalse(phoneNumberValidator(phoneNumber2))
        assertTrue(phoneNumberValidator(phoneNumber3))
    }
}
