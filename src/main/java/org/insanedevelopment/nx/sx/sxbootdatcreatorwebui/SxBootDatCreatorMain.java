package org.insanedevelopment.nx.sx.sxbootdatcreatorwebui;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Conversion;

public class SxBootDatCreatorMain {

	public static void main(String[] args) throws IOException {
		File payloadFile = new File("./hekate_ctcaer_3.2.bin");
		byte[] payloadBytes = FileUtils.readFileToByteArray(payloadFile);
		byte[] bootDatBytes = SxBootDatCreator.convertPayloadToBootDat(payloadBytes);

		FileUtils.writeByteArrayToFile(new File("./boot.dat"), bootDatBytes);
		System.out.println(Hex.encodeHexString(bootDatBytes));
	}

	public static byte[] toByteArray(long value) {
		byte result[] = new byte[4];
		return Conversion.longToByteArray(value, 0, result, 0, 4);
	}
}
