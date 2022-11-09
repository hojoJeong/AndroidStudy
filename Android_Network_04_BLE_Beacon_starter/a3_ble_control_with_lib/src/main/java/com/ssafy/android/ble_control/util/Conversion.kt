/**************************************************************************************************
 * Filename:       Conversion.java
 * Revised:        $Date: 2013-08-30 12:02:37 +0200 (fr, 30 aug 2013) $
 * Revision:       $Revision: 27470 $
 *
 * Copyright 2013 Texas Instruments Incorporated. All rights reserved.
 *
 * IMPORTANT: Your use of this Software is limited to those specific rights
 * granted under the terms of a software license agreement between the user
 * who downloaded the software, his/her employer (which must be your employer)
 * and Texas Instruments Incorporated (the "License").  You may not use this
 * Software unless you agree to abide by the terms of the License.
 * The License limits your use, and you acknowledge, that the Software may not be
 * modified, copied or distributed unless used solely and exclusively in conjunction
 * with a Texas Instruments Bluetooth device. Other than for the foregoing purpose,
 * you may not use, reproduce, copy, prepare derivative works of, modify, distribute,
 * perform, display or sell this Software and/or its documentation for any purpose.
 *
 * YOU FURTHER ACKNOWLEDGE AND AGREE THAT THE SOFTWARE AND DOCUMENTATION ARE
 * PROVIDED 밃S IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, TITLE,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE. IN NO EVENT SHALL
 * TEXAS INSTRUMENTS OR ITS LICENSORS BE LIABLE OR OBLIGATED UNDER CONTRACT,
 * NEGLIGENCE, STRICT LIABILITY, CONTRIBUTION, BREACH OF WARRANTY, OR OTHER
 * LEGAL EQUITABLE THEORY ANY DIRECT OR INDIRECT DAMAGES OR EXPENSES
 * INCLUDING BUT NOT LIMITED TO ANY INCIDENTAL, SPECIAL, INDIRECT, PUNITIVE
 * OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, COST OF PROCUREMENT
 * OF SUBSTITUTE GOODS, TECHNOLOGY, SERVICES, OR ANY CLAIMS BY THIRD PARTIES
 * (INCLUDING BUT NOT LIMITED TO ANY DEFENSE THEREOF), OR OTHER SIMILAR COSTS.
 *
 * Should you have any questions regarding your right to use this Software,
 * contact Texas Instruments Incorporated at www.TI.com
 */
package com.ssafy.android.ble_control.util

import java.util.*
import kotlin.experimental.and

/* This class encapsulates utility functions */
object Conversion {
    fun loUint16(v: Short): Byte {
        return (v and 0xFF).toByte()
    }

    fun hiUint16(v: Short): Byte {
        return (v.toInt() shr 8).toByte()
    }

    fun buildUint16(hi: Byte, lo: Byte): Short {
        return ((hi.toInt() shl 8) + (lo.toInt() and 0xff)).toShort()
    }

    fun BytetohexString(b: ByteArray, len: Int): String {
        val sb = StringBuilder(b.size * (2 + 1))
        val formatter = Formatter(sb)
        for (i in 0 until len) {
            if (i < len - 1) formatter.format("%02X:", b[i]) else formatter.format("%02X", b[i])
        }
        formatter.close()
        return sb.toString()
    }

    fun BytetohexString(b: ByteArray, reverse: Boolean): String {
        val sb = StringBuilder(b.size * (2 + 1))
        val formatter = Formatter(sb)
        if (!reverse) {
            for (i in b.indices) {
                if (i < b.size - 1) formatter.format("%02X:", b[i]) else formatter.format(
                    "%02X",
                    b[i]
                )
            }
        } else {
            for (i in b.size - 1 downTo 0) {
                if (i > 0) formatter.format("%02X:", b[i]) else formatter.format("%02X", b[i])
            }
        }
        formatter.close()
        return sb.toString()
    }

    // Convert hex String to Byte
    fun hexStringtoByte(sb: String?, results: ByteArray): Int {
        var i = 0
        var j = false
        if (sb != null) {
            for (k in 0 until sb.length) {
                if (sb[k] >= '0' && sb[k] <= '9' || sb[k] >= 'a' && sb[k] <= 'f'
                    || sb[k] >= 'A' && sb[k] <= 'F'
                ) {
                    if (j) {
                        results[i] = (results[i] + sb[k].digitToInt(16).toByte()).toByte()
                        i++
                    } else {
                        results[i] = (sb[k].digitToInt(16).shl(4)).toByte()
                    }
                    j = !j
                }
            }
        }
        return i
    }

    fun isAsciiPrintable(str: String?): Boolean {
        if (str == null) {
            return false
        }
        val sz = str.length
        for (i in 0 until sz) {
            if (isAsciiPrintable(str[i]) == false) {
                return false
            }
        }
        return true
    }

    private fun isAsciiPrintable(ch: Char): Boolean {
        return ch.code >= 32 && ch.code < 127
    }
}