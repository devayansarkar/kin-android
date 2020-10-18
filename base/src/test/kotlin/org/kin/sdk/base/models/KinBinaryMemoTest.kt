package org.kin.sdk.base.models

import org.junit.Test
import org.kin.sdk.base.tools.intToByteArray
import org.kin.sdk.base.tools.printBits
import org.kin.sdk.base.tools.toBitSet
import org.kin.sdk.base.tools.toByteArray
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KinBinaryMemoTest {

    @Test
    fun testAgoraEncoding_specicFK() {
        val agoraMemo = KinBinaryMemo.Builder(10, 1, 2)
            .setTransferType(KinBinaryMemo.TransferType.P2P)
            .setForeignKey(byteArrayOf(0xAE.toByte(), 0xFD.toByte()))
            .build()

        val bytes: ByteArray = agoraMemo.encode()

        with(KinBinaryMemo.decode(bytes)) {
            printValues()
            println("foreignKeyBytes: ${agoraMemo.foreignKeyBytes.asList()}\nforeignKeyBytes(): ${this.foreignKeyBytes.asList()}")
            assertTrue { agoraMemo.foreignKeyBytes.contentEquals(this.foreignKeyBytes) }
        }
    }

    @Test
    fun testAgoraEncoding_validFK_lessThanMax() {

        // FK Less than max
        (0..500).forEach {
            println("index: $it")
            val agoraMemo = KinBinaryMemo.Builder(10, 1, 2)
                .setTransferType(KinBinaryMemo.TransferType.P2P)
                .setForeignKey(UUID.randomUUID().toByteArray())
                .build()

            val bytes: ByteArray = agoraMemo.encode()

            bytes.apply {
                printBits("totalbits")
                printBits("totalbits", true)
            }
            agoraMemo.foreignKeyBytes.apply {
                printBits("input.fkbits")
                printBits("input.fkbits", true)
            }

            with(KinBinaryMemo.decode(bytes)) {
                foreignKeyBytes.apply {
                    printBits("fkbits.decoded")
                    printBits("fkbits.decoded", true)
                }
                printValues()
                println("agoraMemo.foreignKeyBytes: ${agoraMemo.foreignKeyBytes.asList()}\n    input.foreignKeyBytes: ${this.foreignKeyBytes.asList()}")
                assertTrue { agoraMemo.foreignKeyBytes.contentEquals(this.foreignKeyBytes) }
            }
        }
    }

    @Test
    fun testAgoraEncoding_validFK_atOrLargerThanMax() {
        // FK at/larger than max (which gets truncated)
        (0..500).forEach {
            println("index: $it")
            val agoraMemo = KinBinaryMemo.Builder(10, 1, 2)
                .setTransferType(KinBinaryMemo.TransferType.P2P)
                .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
                .build()

            val bytes: ByteArray = agoraMemo.encode()

            bytes.apply {
                printBits("totalbits")
                printBits("totalbits", true)
            }
            agoraMemo.foreignKeyBytes.apply {
                printBits("input.fkbits")
                printBits("input.fkbits", true)
            }

            with(KinBinaryMemo.decode(bytes)) {
                foreignKeyBytes.apply {
                    printBits("fkbits.decoded")
                    printBits("fkbits.decoded", true)
                }
                printValues()
                println("agoraMemo.foreignKeyBytes: ${agoraMemo.foreignKeyBytes.asList()}\n    input.foreignKeyBytes: ${this.foreignKeyBytes.asList()}")
                assertTrue { agoraMemo.foreignKeyBytes.contentEquals(this.foreignKeyBytes) }
            }
        }
    }

    @Test
    fun testAgoraEncoding_appIdx_validRange() {
        (0..65535).forEach { index ->
            val agoraMemo = KinBinaryMemo.Builder(index, 3, 7)
                .setTransferType(KinBinaryMemo.TransferType.P2P)
                .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
                .build()

            val bytes: ByteArray = agoraMemo.encode()
            with(KinBinaryMemo.decode(bytes)) {
                printValues()
                assertEquals(index, appIdx)
                assertEquals(3, magicByteIndicator)
                assertEquals(7, version)
                assertEquals(KinBinaryMemo.TransferType.P2P, typeId)
                assertTrue { agoraMemo.foreignKeyBytes.contentEquals(this.foreignKeyBytes) }
            }
        }
    }

    @Test(expected = KinBinaryMemo.Builder.KinBinaryMemoFormatException::class)
    fun testAgoraEncoding_appIdx_invalid_tooSmall() {
        KinBinaryMemo.Builder(appIdx = -1, magicByteIndicator = 1, version = 7)
            .setTransferType(KinBinaryMemo.TransferType.P2P)
            .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
            .build()
    }

    @Test(expected = KinBinaryMemo.Builder.KinBinaryMemoFormatException::class)
    fun testAgoraEncoding_appIdx_invalid_tooLarge() {
        KinBinaryMemo.Builder(65536, 1, 2)
            .setTransferType(KinBinaryMemo.TransferType.P2P)
            .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
            .build()
    }

    @Test(expected = KinBinaryMemo.Builder.KinBinaryMemoFormatException::class)
    fun testAgoraEncoding_typeId_null() {
        KinBinaryMemo.Builder(65536, 1, 2)
            .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
            .build()
    }

    @Test
    fun testAgoraEncoding_no_fk_default_0s() {
        val memo = KinBinaryMemo.Builder(123, 1, 2)
            .setTransferType(KinBinaryMemo.TransferType.P2P)
            .build()

        assertTrue {
            byteArrayOf(
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            ).contentEquals(memo.foreignKeyBytes)
        }
    }

    @Test
    fun testAgoraEncoding_magicByteIndicator_validRange() {
        (0..3).forEach { index ->
            val agoraMemo = KinBinaryMemo.Builder(65535, index, 7)
                .setTransferType(KinBinaryMemo.TransferType.P2P)
                .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
                .build()

            val bytes: ByteArray = agoraMemo.encode()
            bytes.printBits("totalbits", true)
            with(KinBinaryMemo.decode(bytes)) {
                printValues()
                assertEquals(65535, appIdx)
                assertEquals(index, magicByteIndicator)
                assertEquals(7, version)
                assertEquals(KinBinaryMemo.TransferType.P2P, typeId)
                assertTrue { agoraMemo.foreignKeyBytes.contentEquals(this.foreignKeyBytes) }
            }
        }
    }

    @Test(expected = KinBinaryMemo.Builder.KinBinaryMemoFormatException::class)
    fun testAgoraEncoding_magicByteIndicator_invalid_tooSmall() {
        KinBinaryMemo.Builder(65535, -1, 7)
            .setTransferType(KinBinaryMemo.TransferType.P2P)
            .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
            .build()
    }

    @Test(expected = KinBinaryMemo.Builder.KinBinaryMemoFormatException::class)
    fun testAgoraEncoding_magicByteIndicator_invalid_tooLarge() {
        KinBinaryMemo.Builder(65535, 3, 8)
            .setTransferType(KinBinaryMemo.TransferType.P2P)
            .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
            .build()
    }

    @Test
    fun testAgoraEncoding_version_validRange() {
        (0..7).forEach { index ->
            val agoraMemo = KinBinaryMemo.Builder(65535, 3, index)
                .setTransferType(KinBinaryMemo.TransferType.P2P)
                .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
                .build()

            val bytes: ByteArray = agoraMemo.encode()
            with(KinBinaryMemo.decode(bytes)) {
                printValues()
                assertEquals(65535, appIdx)
                assertEquals(3, magicByteIndicator)
                assertEquals(index, version)
                assertEquals(KinBinaryMemo.TransferType.P2P, typeId)
                assertTrue { agoraMemo.foreignKeyBytes.contentEquals(this.foreignKeyBytes) }
            }
        }
    }

    @Test(expected = KinBinaryMemo.Builder.KinBinaryMemoFormatException::class)
    fun testAgoraEncoding_version_invalid_tooSmall() {
        KinBinaryMemo.Builder(65535, 3, -1)
            .setTransferType(KinBinaryMemo.TransferType.P2P)
            .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
            .build()
    }

    @Test(expected = KinBinaryMemo.Builder.KinBinaryMemoFormatException::class)
    fun testAgoraEncoding_version_invalid_tooLarge() {
        KinBinaryMemo.Builder(65535, 3, 8)
            .setTransferType(KinBinaryMemo.TransferType.P2P)
            .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
            .build()
    }

    @Test
    fun testAgoraEncoding_typeId_validRange() {

        fun verifyTypeId(inputTypeId: KinBinaryMemo.TransferType) {
            val agoraMemo = KinBinaryMemo.Builder(65535, 3, 7)
                .setTransferType(inputTypeId)
                .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
                .build()

            val bytes: ByteArray = agoraMemo.encode()
            with(KinBinaryMemo.decode(bytes)) {
                printValues()
                assertEquals(65535, appIdx)
                assertEquals(3, magicByteIndicator)
                assertEquals(7, version)
                assertEquals(inputTypeId, typeId)
                assertTrue { agoraMemo.foreignKeyBytes.contentEquals(this.foreignKeyBytes) }
            }
        }

        verifyTypeId(KinBinaryMemo.TransferType.None)
        verifyTypeId(KinBinaryMemo.TransferType.Earn)
        verifyTypeId(KinBinaryMemo.TransferType.Spend)
        verifyTypeId(KinBinaryMemo.TransferType.P2P)

    }

    @Test(expected = KinBinaryMemo.Builder.KinBinaryMemoFormatException::class)
    fun testAgoraEncoding_typeId_invalid_tooSmall() {
        KinBinaryMemo.Builder(65535, 3, -1)
            .setTransferType(KinBinaryMemo.TransferType.ANY(-1))
            .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
            .build()
    }

    @Test(expected = KinBinaryMemo.Builder.KinBinaryMemoFormatException::class)
    fun testAgoraEncoding_typeId_invalid_tooLarge() {
        KinBinaryMemo.Builder(65535, 3, 8)
            .setTransferType(KinBinaryMemo.TransferType.ANY(32))
            .setForeignKey(UUID.randomUUID().toByteArray() + UUID.randomUUID().toByteArray())
            .build()
    }


    // Utils

    private fun KinBinaryMemo.printValues() {
        println("AgoraMemoValues {\n  ${toString()}")
        magicByteIndicator.intToByteArray().printBits(" magicByteIndicator", true)
        version.intToByteArray().printBits(" version", true)
        typeId.value.intToByteArray().printBits(" typeId", true)
        appIdx.intToByteArray().printBits(" appIdx", true)
        foreignKeyBytes.toBitSet().apply {
            printBits(" foreignKeyBytes", true, showByteOffset = true, size = size())
        }
        encode().printBits(" binary", true)
        println("}")
    }
}
