// IBookManagerInterface.aidl
package com.sparkfengbo.app.android.aidltest;

// Declare any non-default types here with import statements
import com.sparkfengbo.app.android.aidltest.Book;

interface IBookManagerInterface {

   List<Book> getList();

   void addBook(in Book book);
}