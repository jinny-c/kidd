package com.kidd.base.common.secure;

import java.security.SecureRandom;

public class SecureManager {

	private byte[] seed = null;
	private byte[] deskey = null;
	private long beginTime = -1;
	protected String securekeyindex = null;

	public SecureManager() {
		createNewSeed();
	}

	private void createNewSeed() {
		int i;
		byte[] ct = new byte[8];
		byte[] desKey1 = { 0x68, 0x61, 0x6e, 0x64, 0x70, 0x61, 0x79, 0x21 };

		this.seed = new byte[16];
		// initseed
		SecureRandom random = new SecureRandom();
		random.setSeed(System.currentTimeMillis());
		for (i = 0; i < 16; i++) {
			double dr = random.nextDouble();
			dr = dr * 0xff;
			int ri = (int) dr;
			this.seed[i] = (byte) ri;
		}

		for (i = 0; i < 8; i++) {
			ct[i] = (byte) (this.seed[i] ^ this.seed[i + 8]);
		}
		DesEncrypt des = new DesEncrypt();
		this.deskey = des.Des(desKey1, ct, 1);
		this.securekeyindex = null;
		beginTime = -1;
	}

	private byte[] desfor8(byte[] data, byte[] key, int flag) {
		if (data.length % 8 != 0 || (key != null && key.length != 8)) {
			return null;
		}
		if (flag == 1) {
			CheckDesKey();
		}
		byte[] rdata = new byte[data.length];
		byte[] keyb = this.deskey;
		if (key != null) {
			keyb = key;
		}
		for (int i = 0; i < data.length / 8; i++) {
			DesEncrypt des = new DesEncrypt();
			byte[] desdata = new byte[8];
			for (int j = 0; j < 8; j++) {
				desdata[j] = data[i * 8 + j];
			}
			byte[] rdes = des.Des(keyb, desdata, flag);
			if (rdes != null && rdes.length == 8) {
				for (int j = 0; j < 8; j++) {
					rdata[i * 8 + j] = rdes[j];
				}
			} else {
				return null;
			}
		}
		return rdata;
	}

	private int converCtoI(byte c) {
		if (c >= 48 && c < 58) {
			return c - 48;
		} else if (c >= 65 && c < 71) {
			return c - 65 + 10;
		} else if (c >= 97 && c < 103) {
			return c - 97 + 10;
		}
		return 0;
	}

	private byte[] hexTobyte(String hex) {
		if (hex != null) {
			byte[] source = hex.getBytes();
			byte[] resultdata = new byte[source.length / 2];
			for (int i = 0; i < source.length / 2; i++) {
				int tempi = converCtoI(source[2 * i]);
				resultdata[i] = (byte) ((tempi << 4) & 0xf0);
				resultdata[i] = (byte) (resultdata[i] + converCtoI(source[2 * i + 1]));
			}
			return resultdata;
		}
		return null;
	}

	private String byteTohex(byte[] data) {
		if (data != null) {
			char ac[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'A', 'B', 'C', 'D', 'E', 'F' };
			char[] red = new char[data.length * 2];
			for (int i = 0; i < data.length; i++) {
				red[2 * i] = ac[data[i] >> 4 & 0xf];
				red[2 * i + 1] = ac[data[i] & 0xf];
			}
			return new String(red);
		}
		return null;
	}

	public String Des(String data, int flag, String key) {
		if (data != null && data.length() != 0) {
			try {
				// treate data
				byte[] datas = data.getBytes("UTF-8");
				if (flag == 0){
					datas = this.hexTobyte(data);
				}
				int num = (int) ((datas.length + 7) / 8) * 8;
				byte[] desbyte = new byte[num];
				for (int i = 0; i < num; i++) {
					if (i < datas.length) {
						desbyte[i] = datas[i];
					} else {
						desbyte[i] = 0;
					}
				}

				// treate key
				byte[] keybyte = null;
				if (key != null) {
					keybyte = hexTobyte(key);
				}
				byte[] tmp = this.desfor8(desbyte, keybyte, flag);
				if (flag == 0) {
					return new String(tmp, "utf-8");
				} else {
					return this.byteTohex(tmp);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	private void CheckDesKey() {
		if (this.beginTime != -1) {
			long notime = System.currentTimeMillis();
			if ((notime - this.beginTime) > 1000 * 60 * 60 * 6) {
				this.createNewSeed();
			}
		}
	}

	public void ReSetSecure(String keExchange) {
		if (keExchange != null) {
			this.securekeyindex = keExchange;
			this.beginTime = System.currentTimeMillis();
		} else {
			this.createNewSeed();
		}
	}

	// 用于加解密客户端本地的数据
	public String Cupdes(String data, int flag) { // flag = 1 加密，flag =0, 解密

		String rDigits = null;

		String sID = "";

		Secure s = new Secure(rDigits, sID.getBytes());

		if (flag == 1) {
			String sData = "000000000" + data;
			sData = sData.substring(sData.length() - 8);
			return s.des8Bytes(sData.getBytes(), flag);
		} else {
			return s.des8Bytes(s.hexByte(data), flag);
		}
	}
}
