// IMemoryService.aidl
package com.sparkfengbo.app.android.aidltest;

// Declare any non-default types here with import statements

interface IMemoryService {

   ParcelFileDescriptor getFileDescriptor();

   void setValue(String value);
}
