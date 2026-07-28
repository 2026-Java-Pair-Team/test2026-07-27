
package project1;

public class NumberGame {
	public String judge(int num) {
		if (num == 777) {
			return "ラッキーセブンです";
		} else if (num % 2 == 0) {
			return "偶数です";
		} else {
			return "奇数です";
		}
	}
}
