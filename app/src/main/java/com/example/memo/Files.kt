package com.example.memo

import android.os.Environment
import java.io.*
import java.text.DateFormat
import java.util.*

private fun getFilesDir() : File {
    val publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    if (publicDir != null) {
        //　存在しないディレクトリが返された場合は、作成する
        if (!publicDir.exists()) publicDir.mkdirs()
        return publicDir
    } else {
        val dir = File(Environment.getExternalStorageDirectory(), "MemoFiles")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }
}

fun getFiles() = getFilesDir().listFiles().toList()

fun outputFile(original: File?, content: String) : File {
    //　ファイル名は「memo- タイムスタンプ」とする
    val timeStamp = DateFormat.format("yyyy-MM-dd-hh-mm-ss", Date())

    val file = original ?: File(getFiles(), "memo-$timeStamp")

    val writer = BufferedWriter(FileWriter(file))
    writer.use {
        it.write(content)
        it.flush()
    }

    return file
}

fun inputFile(file: File) : String {
    val reader = BufferedReader(FileReader(file))
    return reader.readLines().joinToString("¥n")
}