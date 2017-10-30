package com.kidd.base.common.secure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * APDU命令类。封装所有支持的APDU命令，生成APDU命令数据。
 * 
 */
public final class ApduCommand {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 建构函数。初始化APDU命令。
	 */
	public ApduCommand() {
		export_flag = EXPORT_ALL;
		apdu_head = new byte[4];
		lc = 0;
		body_data = null;
		le = 0;
	}

	/**
	 * 生成APDU命令数据。
	 * 
	 * @return APDU命令数据字节数组。
	 * @throws IOException
	 *             如果出现I/O错误。
	 */
	private byte[] build() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(apdu_head);

			if ((export_flag & EXPORT_CMD_DATA_LENGTH) != 0) {
				baos.write(lc);
			}
			if ((export_flag & EXPORT_BODY_DATA) != 0) {
				baos.write(body_data);
			}
			if ((export_flag & EXPORT_EXPECTED_LENGTH) != 0) {
				baos.write(le);
			}
			byte[] data = baos.toByteArray();
			baos.flush();
			return data;
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException ex) {
				}

			}
			if(body_data != null){
				body_data = new byte[0];
			}
			baos = null;

		}
	}

	/**
	 * @deprecated
	 * @return cmd byte array
	 */
	public byte[] active() {
		try {
			apdu_head[OFFSET_CLA] = CLA_80;
			apdu_head[OFFSET_INS] = (byte) 0xE0;
			apdu_head[OFFSET_P1] = (byte) 0x00;
			apdu_head[OFFSET_P2] = (byte) 0x00;

			export_flag = EXPORT_NONE;
			return build();
		} catch (Exception ex) {
			logger.error(ex);
		}

		return null;
	}

	/**
	 * 选择应用命令。该命令定义了用AID选择卡上应用的方式或用FID选择应用文件的方式。
	 * 
	 * @param para1
	 *            参数P1。只能是<a href="#SELECT_EF">SELECT_EF</a>或者<a
	 *            href="#SELECT_DF">SELECT_DF</a>。
	 * @param id
	 *            报文的数据域。当P1='0'时，命令报文数据域为EF的EFID。当P1=
	 *            '04'时，命令报文数据域为DF的AID。如果DF是移动支付应用，则AID为"A0 00 00 03 33
	 *            CUP-MOBILE"。
	 * @throws IOException
	 *             如果出现I/O错误。
	 * @return 选择应用命令数据，命令数据出错返回null。
	 */
	public byte[] select(int para1, byte[] id) throws IOException {
		if (para1 != SELECT_EF && para1 != SELECT_DF) {
			return null;
		}
		if (id == null) {
			return null;
		}
		apdu_head[OFFSET_CLA] = CLA_00;
		apdu_head[OFFSET_INS] = INS_SELECT;
		apdu_head[OFFSET_P1] = (byte) para1;
		apdu_head[OFFSET_P2] = (byte) 0x00;

		body_data = id;

		lc = (byte) id.length;

		export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;
		return build();
	}

	public byte[] readBinary(int fileNo, int offset, int readLen)
			throws IOException {
		apdu_head[OFFSET_CLA] = CLA_00;
		apdu_head[OFFSET_INS] = INS_READ_BINARY;
		apdu_head[OFFSET_P1] = (byte) (READ_BINARY_MODE | (fileNo & 0x1f));
		apdu_head[OFFSET_P2] = (byte) offset;

		le = (byte) readLen;

		export_flag = EXPORT_EXPECTED_LENGTH;

		return build();
	}

	public byte[] readRecord(int recordNo, int fileNo, int readLen)
			throws IOException {
		apdu_head[OFFSET_CLA] = CLA_00;
		apdu_head[OFFSET_INS] = INS_READ_RECORD;
		apdu_head[OFFSET_P1] = (byte) recordNo;
		apdu_head[OFFSET_P2] = (byte) (((fileNo << 3) & 0xf8) | READ_RECORD_MODE);

		le = (byte) readLen;

		export_flag = EXPORT_EXPECTED_LENGTH;

		return build();
	}

	public byte[] updateBinary(int fileNo, int offset, byte[] newData)
			throws IOException {
		apdu_head[OFFSET_CLA] = CLA_00;
		apdu_head[OFFSET_INS] = INS_UPDATE_BINARY;
		apdu_head[OFFSET_P1] = (byte) (READ_BINARY_MODE | (fileNo & 0x1f));
		apdu_head[OFFSET_P2] = (byte) offset;

		lc = (byte) newData.length;
		body_data = newData;

		export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;

		return build();
	}

	public byte[] updateRecord(int recordNo, int fileNo, byte[] newData)
			throws IOException {
		apdu_head[OFFSET_CLA] = CLA_00;
		apdu_head[OFFSET_INS] = INS_UPDATE_RECORD;
		apdu_head[OFFSET_P1] = (byte) recordNo;
		apdu_head[OFFSET_P2] = (byte) (((fileNo << 3) & 0xf8) | READ_RECORD_MODE);

		lc = (byte) newData.length;
		body_data = newData;

		export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;

		return build();
	}

	public byte[] verify(byte[] password) throws IOException {
		apdu_head[OFFSET_CLA] = CLA_00;
		apdu_head[OFFSET_INS] = INS_VERIFY;
		apdu_head[OFFSET_P1] = (byte) 0x00;
		apdu_head[OFFSET_P2] = (byte) 0x00;

		lc = 0x08;
		body_data = password;

		export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;

		return build();
	}

	public byte[] unblockPin() {
		return null;
	}

	public byte[] changePin(int type, byte[] op, byte[] np) throws IOException {
		apdu_head[OFFSET_CLA] = CLA_80;
		apdu_head[OFFSET_INS] = INS_CHANGE_PIN;
		apdu_head[OFFSET_P1] = (byte) type;
		apdu_head[OFFSET_P2] = (byte) 0x00;

		body_data = new byte[op.length + 1 + np.length];
		System.arraycopy(op, 0, body_data, 0, op.length);
		body_data[op.length] = (byte) 0xff;
		System.arraycopy(np, 0, body_data, op.length + 1, np.length);

		lc = (byte) body_data.length;

		export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;

		return build();
	}

	public byte[] initForDEScrypt(int keyId, int keyIndex, byte[] keyData)
			throws IOException {
		apdu_head[OFFSET_CLA] = CLA_80;
		apdu_head[OFFSET_INS] = INS_INIT_FOR_DESCRYPT;
		apdu_head[OFFSET_P1] = (byte) keyId;
		apdu_head[OFFSET_P2] = (byte) keyIndex;

		lc = (byte) keyData.length;
		body_data = keyData;

		export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;

		return build();
	}

	public byte[] desCrypt(byte mode, byte[] data) throws IOException {
		apdu_head[OFFSET_CLA] = CLA_80;
		apdu_head[OFFSET_INS] = INS_DES_CRYPT;
		apdu_head[OFFSET_P1] = mode;
		apdu_head[OFFSET_P2] = (byte) 0x00;

		lc = (byte) data.length;
		body_data = data;

		export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;

		return build();
	}

	public byte[] getResponse() {
		return null;
	}

	public byte[] getBankCardFileEntry(int mode, byte[] bankCardNo)
			throws IOException {
		apdu_head[OFFSET_CLA] = CLA_80;
		apdu_head[OFFSET_INS] = INS_GET_BANKCARD_FILE_ENTRY;
		apdu_head[OFFSET_P1] = (byte) 0x00;
		apdu_head[OFFSET_P2] = (byte) mode;

		if (mode == 0x02) {
			lc = (byte) bankCardNo.length;
			body_data = bankCardNo;

			export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;
		} else {
			export_flag = EXPORT_NONE;
		}
		return build();
	}

	public byte[] getBankCardInfo(int recordNo, byte[] bcdDate)
			throws IOException {
		apdu_head[OFFSET_CLA] = CLA_80;
		apdu_head[OFFSET_INS] = INS_GET_BANKCARD_INFO;
		apdu_head[OFFSET_P1] = (byte) 0x02;
		apdu_head[OFFSET_P2] = (byte) recordNo;

		lc = (byte) bcdDate.length;
		body_data = bcdDate;

		export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;

		return build();
	}

	public byte[] rename(int recordNo, byte[] cardName) throws IOException {
		apdu_head[OFFSET_CLA] = CLA_80;
		apdu_head[OFFSET_INS] = INS_RENAME;
		apdu_head[OFFSET_P1] = (byte) recordNo;
		apdu_head[OFFSET_P2] = (byte) 0x24;

		body_data = new byte[0x14];
		System.arraycopy(cardName, 0, body_data, 0, cardName.length);

		lc = (byte) body_data.length;

		export_flag = EXPORT_CMD_DATA_LENGTH | EXPORT_BODY_DATA;

		return build();
	}

	public byte[] writeKey() {
		return null;
	}

	public byte[] accessSwitch() {
		return null;
	}

	public byte[] getChallenge() {
		return null;
	}

	public byte getHeaderProp(int offset) {
		return apdu_head[offset];
	}

	public int getLc() {
		return lc;
	}

	public byte[] getBodyData() {
		return body_data;
	}

	public int getLe() {
		return le;
	}

	/**
	 * APDU命令头。包括命令类型，指令代码，指令参数1，指令参数2。
	 */
	private byte[] apdu_head;
	/**
	 * APDU命令数据域中存在的字节数。
	 */
	private byte lc;
	/**
	 * APDU命令发送的数据位串，其长度为lc。
	 */
	private byte[] body_data;
	/**
	 * 响应数据域中期望的最大数据字节数。
	 */
	private byte le;

	/**
	 * APDU条件体导出标记。
	 */
	private byte export_flag;

	// apdu head offset
	public static final byte OFFSET_CLA = (byte) 0x00;
	public static final byte OFFSET_INS = (byte) 0x01;
	public static final byte OFFSET_P1 = (byte) 0x02;
	public static final byte OFFSET_P2 = (byte) 0x03;

	// /command select parameter 1
	public static final byte SELECT_EF = (byte) 0x00;
	public static final byte SELECT_DF = (byte) 0x04;

	// read mode
	public static final byte READ_BINARY_MODE = (byte) 0x80;

	public static final byte READ_RECORD_MODE = (byte) 0x04;

	public static final byte DES_CRYPT_NO_FOLLOWING = (byte) 0x00;
	public static final byte DES_CRYPT_LAST_MAC = (byte) 0x01;
	public static final byte DES_CRYPT_FOLLOWING = (byte) 0x02;
	public static final byte DES_CRYPT_NEXT_MAC = (byte) 0x03;
	public static final byte DES_CRYPT_EXCLUSIVE_MAC = (byte) 0x05;
	public static final byte DES_CRYPT_FIRST_MAC = (byte) 0x07;

	private static final byte EXPORT_NONE = (byte) 0x00;
	private static final byte EXPORT_CMD_DATA_LENGTH = (byte) 0x01;
	public static final byte startofdata = (byte) 0x01;
	private static final byte EXPORT_BODY_DATA = (byte) 0x02;
	private static final byte EXPORT_EXPECTED_LENGTH = (byte) 0x04;
	private static final byte EXPORT_ALL = (byte) 0x07;

	public static final byte CLA_00 = (byte) 0x00;
	public static final byte CLA_80 = (byte) 0x80;
	public static final byte CLA_84 = (byte) 0x84;

	public static final byte INS_SELECT = (byte) 0xA4;
	public static final byte INS_READ_BINARY = (byte) 0xB0;
	public static final byte INS_READ_RECORD = (byte) 0xB2;
	public static final byte INS_RENAME = (byte) 0xD4;
	public static final byte INS_UPDATE_BINARY = (byte) 0xD6;
	public static final byte INS_UPDATE_RECORD = (byte) 0xDC;
	public static final byte INS_VERIFY = (byte) 0x20;
	public static final byte INS_UNBLOCK_PIN = (byte) 0x2C;
	public static final byte INS_CHANGE_PIN = (byte) 0x5E;
	public static final byte endofdata = (byte) 0x08;
	public static final byte INS_INIT_FOR_DESCRYPT = (byte) 0x1A;
	public static final byte INS_DES_CRYPT = (byte) 0xFA;
	public static final byte INS_GET_RESPONSE = (byte) 0xC0;
	public static final byte INS_GET_BANKCARD_FILE_ENTRY = (byte) 0xF2;
	public static final byte INS_GET_BANKCARD_INFO = (byte) 0xF8;
	public static final byte INS_WRITE_KEY = (byte) 0xF4;
	public static final byte INS_ACCESS_SWITCH = (byte) 0xF6;
	public static final byte INS_GET_CHALLENGE = (byte) 0x84;

	public static final String[] COMMAND_NAME = { "Select", "Read Binary",
			"Read Record", "Update Binary", "Update Binary", "Verify",
			"Unblock PIN", "Change PIN", "INI_FOR_DESCRYPT", "DES Crypt",
			"Get Response", "Get BankCard Info", "Write Key", "Access Switch",
			"Get Challenge", "Active" };
}

// UNBLOCKPIN
// CHANGEPIN
// GETRESPONSE
// WRITEKEY
// ACCESSSWITCH
// GETCHALLENGE
