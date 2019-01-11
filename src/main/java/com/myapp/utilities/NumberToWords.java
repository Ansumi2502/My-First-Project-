/**
 * Created By Anamika Pandey
 */
package com.myapp.utilities;

public final class NumberToWords {

	private static String string;

	private static String a[] = { "", "One", "Two", "Three", "Four", "Five",
			"Six", "Seven", "Eight", "Nine", };

	private static String b[] = { "Hundred", "Thousand", "Lakh", "Crore"

	};

	private static String c[] = { "Ten", "Eleven", "Twelve", "Thirteen",
			"Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
			"Ninteen", };

	private static String d[] = {

	"Twenty", "Thirty", "Fourty", "Fifty", "Sixty", "Seventy", "Eighty",
			"Ninty" };

	private NumberToWords() {

	}
	// parameter type changed from int to long to support big values
	public static String convertNumToWord(long number) {
		number = Math.abs(number);
		int c = 1;
		long rm;
		string = "";
		while (number != 0) {
			switch (c) {
			case 1:
				rm = number % 100;
				pass(rm);
				if (number > 100 && number % 100 != 0) {
					display("and ");
				}
				number /= 100;

				break;

			case 2:
				rm = number % 10;
				if (rm != 0) {
					display(" ");
					display(b[0]);
					display(" ");
					pass(rm);
				}
				number /= 10;
				break;

			case 3:
				rm = number % 100;
				if (rm != 0) {
					display(" ");
					display(b[1]);
					display(" ");
					pass(rm);
				}
				number /= 100;
				break;

			case 4:
				rm = number % 100;
				if (rm != 0) {
					display(" ");
					display(b[2]);
					display(" ");
					pass(rm);
				}
				number /= 100;
				break;

			case 5:
				rm = number % 100;
				if (rm != 0) {
					display(" ");
					display(b[3]);
					display(" ");
					pass(rm);
				}
				number /= 100;
				break;

			}
			c++;
		}		
		return string;
	}

	private static void pass(long number) {
		int rm, q;
		if (number < 10) {
			display(a[(int)number]);
		}

		if (number > 9 && number < 20) {
			display(c[(int)number - 10]);
		}

		if (number > 19) {
			rm = (int)number % 10;
			if (rm == 0) {
				q =(int) number / 10;
				display(d[q - 2]);
			} else {
				q =(int) number / 10;
				display(a[rm]);
				display(" ");
				display(d[q - 2]);
			}
		}
	}

	private static void display(String s) {
		String t;
		t = string;
		string = s;
		string += t;
	}

}
