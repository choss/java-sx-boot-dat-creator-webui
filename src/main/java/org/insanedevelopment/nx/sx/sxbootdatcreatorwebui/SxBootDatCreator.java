package org.insanedevelopment.nx.sx.sxbootdatcreatorwebui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Conversion;

public class SxBootDatCreator {

	private static final Charset CHARSET = StandardCharsets.US_ASCII;
	private static DigestUtils DIGEST_UTILS = new DigestUtils(MessageDigestAlgorithms.SHA_256);
	private static String NAME = "Insane BOOT";
	private static String VERSION = "V1.0";
	private static long ENCRYPTION_FLAG_NO_ENCRYPTION = 0;
	private static long BASE_ADDRESS = 0x40010000;
	private static byte[] PADDING = new byte[0xA4];

	public static byte[] convertPayloadToBootDat(byte[] payloadBytes) throws IOException {
		if (payloadBytes.length < 10) {
			throw new IOException("Payload too small " + payloadBytes.length);
		}

		byte[] payloadDigest = DIGEST_UTILS.digest(payloadBytes);

		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		// my shoutout
		IOUtils.write(NAME, bas, CHARSET);
		// which is 0 terminated
		bas.write(0);
		// Version
		IOUtils.write(VERSION, bas, CHARSET);

		IOUtils.write(payloadDigest, bas);
		IOUtils.write(SxBootDatCreator.toByteArray(BASE_ADDRESS), bas);
		IOUtils.write(SxBootDatCreator.toByteArray(payloadBytes.length), bas);
		IOUtils.write(SxBootDatCreator.toByteArray(ENCRYPTION_FLAG_NO_ENCRYPTION), bas);
		IOUtils.write(PADDING, bas);

		byte[] headerDigest = DIGEST_UTILS.digest(bas.toByteArray());
		IOUtils.write(headerDigest, bas);
		IOUtils.write(payloadBytes, bas);

		return bas.toByteArray();
	}

	public static byte[] toByteArray(long value) {
		byte result[] = new byte[4];
		return Conversion.longToByteArray(value, 0, result, 0, 4);
	}
}
