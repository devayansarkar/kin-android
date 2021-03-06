// Automatically generated by xdrgen 
// DO NOT EDIT or your changes may be overwritten
package org.kin.stellarfork.xdr

import java.io.IOException

// === xdr source ============================================================
//  struct ClaimOfferAtom
//  {
//      // emitted to identify the offer
//      AccountID sellerID; // Account that owns the offer
//      uint64 offerID;
//
//      // amount and asset taken from the owner
//      Asset assetSold;
//      int64 amountSold;
//
//      // amount and asset sent to the owner
//      Asset assetBought;
//      int64 amountBought;
//  };
//  ===========================================================================
class ClaimOfferAtom {
    var sellerID: AccountID? = null
    var offerID: Uint64? = null
    var assetSold: Asset? = null
    var amountSold: Int64? = null
    var assetBought: Asset? = null
    var amountBought: Int64? = null

    companion object {
        @JvmStatic
        @Throws(IOException::class)
        fun encode(
            stream: XdrDataOutputStream,
            encodedClaimOfferAtom: ClaimOfferAtom
        ) {
            AccountID.encode(stream, encodedClaimOfferAtom.sellerID!!)
            Uint64.encode(stream, encodedClaimOfferAtom.offerID!!)
            Asset.encode(stream, encodedClaimOfferAtom.assetSold!!)
            Int64.encode(stream, encodedClaimOfferAtom.amountSold!!)
            Asset.encode(stream, encodedClaimOfferAtom.assetBought!!)
            Int64.encode(stream, encodedClaimOfferAtom.amountBought!!)
        }

        @JvmStatic
        @Throws(IOException::class)
        fun decode(stream: XdrDataInputStream): ClaimOfferAtom {
            val decodedClaimOfferAtom = ClaimOfferAtom()
            decodedClaimOfferAtom.sellerID = AccountID.decode(stream)
            decodedClaimOfferAtom.offerID = Uint64.decode(stream)
            decodedClaimOfferAtom.assetSold = Asset.decode(stream)
            decodedClaimOfferAtom.amountSold = Int64.decode(stream)
            decodedClaimOfferAtom.assetBought = Asset.decode(stream)
            decodedClaimOfferAtom.amountBought = Int64.decode(stream)
            return decodedClaimOfferAtom
        }
    }
}
