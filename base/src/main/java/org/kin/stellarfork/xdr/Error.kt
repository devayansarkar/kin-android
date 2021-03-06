// Automatically generated by xdrgen 
// DO NOT EDIT or your changes may be overwritten
package org.kin.stellarfork.xdr

import java.io.IOException

// === xdr source ============================================================
//  struct Error
//  {
//      ErrorCode code;
//      string msg<100>;
//  };
//  ===========================================================================
class Error {
    var code: ErrorCode? = null
    var msg: String? = null

    companion object {
        @JvmStatic
        @Throws(IOException::class)
        fun encode(
            stream: XdrDataOutputStream,
            encodedError: Error
        ) {
            ErrorCode.encode(stream, encodedError.code!!)
            stream.writeString(encodedError.msg!!)
        }

        @JvmStatic
        @Throws(IOException::class)
        fun decode(stream: XdrDataInputStream): Error {
            val decodedError = Error()
            decodedError.code = ErrorCode.decode(stream)
            decodedError.msg = stream.readString()
            return decodedError
        }
    }
}
