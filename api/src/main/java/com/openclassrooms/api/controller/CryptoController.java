package com.openclassrooms.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController {


	// Chiffrement Cesar :
	@GetMapping("/crypto/{value}/{shift}")
	public String getString(@PathVariable("value") String value,@PathVariable("shift") int shift) {
		char[] chars = value.toCharArray();
		for (int i=0; i < value.length(); i++)
		{
			char c = chars[i];
			if (c >= 32 && c <= 127)
			{
				int x = c - 32;
				x = (x + shift) % 96;
				if (x < 0)
					x += 96;
				chars[i] = (char) (x + 32);
			}
		}
		return new String(chars);
	}

	@GetMapping("/crypt/{ch}/{lg}")
	private  String Decryptage(@PathVariable("ch") String ch, @PathVariable("lg") int lg) {
		String crypt = ""; int j = 0; char c= ' ';
		for(int i=0;i<ch.length();++i) {
			c = ch.charAt(i);
			j = ((int) c - lg);
			if(j < 97 ) {
				j+=26;
			}
			crypt+=(char)j;
		}
		return crypt;
	}
	public  String vigenere(String plaintext, String key, boolean encrypt) {
System.out.println(plaintext);
System.out.println(key);
		final int textSize = plaintext.length();
		final int keySize = key.length();

		final int groupSize1 = 'Z' - 'A' + 1;
		final int groupSize2 = 'z' - 'a' + 1;
		final int totalGroupSize = groupSize1 + groupSize2;

		final StringBuilder encryptedText = new StringBuilder(textSize);
		for (int i = 0; i < textSize; i++) {
			final char plainChar = plaintext.charAt(i);

			// this should be a method, called for both the plain text as well as the key
			final int plainGroupNumber;
			if (plainChar >= 'A' && plainChar <= 'Z') {
				plainGroupNumber = plainChar - 'A';
			} else if (plainChar >= 'a' && plainChar <= 'z') {
				plainGroupNumber = groupSize1 + plainChar - 'a';
			} else {
				// simply leave spaces and other characters
				encryptedText.append(plainChar);
				continue;
			}

			final char keyChar = key.charAt(i % keySize);
			final int keyGroupNumber;
			if (keyChar >= 'A' && keyChar <= 'Z') {
				keyGroupNumber = keyChar - 'A';
			} else if (keyChar >= 'a' && keyChar <= 'z') {
				keyGroupNumber = groupSize1 + keyChar - 'a';
			} else {
				throw new IllegalStateException("Invalid character in key");
			}

			// this should be a separate method
			final int cipherGroupNumber;
			if (encrypt) {
				cipherGroupNumber = (plainGroupNumber + keyGroupNumber) % totalGroupSize;
			} else {
				// some code to go around the awkward way of handling % in Java for negative numbers
				final int someCipherGroupNumber = plainGroupNumber - keyGroupNumber;
				if (someCipherGroupNumber < 0) {
					cipherGroupNumber = (someCipherGroupNumber + totalGroupSize);
				} else {
					cipherGroupNumber = someCipherGroupNumber;
				}
			}

			// this should be a separate method
			final char cipherChar;
			if (cipherGroupNumber < groupSize1) {
				cipherChar = (char) ('A' + cipherGroupNumber);
			} else {
				cipherChar = (char) ('a' + cipherGroupNumber - groupSize1);
			}
			encryptedText.append(cipherChar);
		}

		return encryptedText.toString();
	}
	// Vigenere
	@GetMapping("/cryp/{text}/{key}")
	public  String vigenereEncrypt(@PathVariable("text") String text, @PathVariable("key")  String key)
	{

	return vigenere(text,key,false);
	}
	@GetMapping("/cry/{text}/{key}")
	public  String VigenereDecrypt(@PathVariable("text")String text, @PathVariable("key") String key)
	{
		String orig_text="";

		for (int i = 0 ; i < text.length() &&
				i < key.length(); i++)
		{
			// converting in range 0-25
			int x = (text.charAt(i) +
					key.charAt(i) + 26) %26;

			// convert into alphabets(ASCII)
			x += 'A';
			orig_text+=(char)(x);
		}
		return orig_text;}




}