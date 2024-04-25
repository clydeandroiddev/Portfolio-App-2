package com.jczm.dataloader.util.extensions

fun String.isArabic(): Boolean {
    val textWithoutSpace = trim { it <= ' ' }.replace(" ".toRegex(), "") //to ignore whitepace
    var i = 0
    while (i < textWithoutSpace.length) {
        val c = textWithoutSpace.codePointAt(i)
        //range of arabic chars/symbols is from 0x0600 to 0x06ff
        //the arabic letter 'لا' is special case having the range from 0xFE70 to 0xFEFF
        i += if (c in 0x0600..0x06FF || c in 0xFE70..0xFEFF) Character.charCount(c) else return false
    }
    return true
}