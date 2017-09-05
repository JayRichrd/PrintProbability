package com.jy;

import java.util.Scanner;

public class Main {
	// 单个色子的最大点数
	private static int MAX_VALUE = 6;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入色子个数n:");
		int n = scanner.nextInt();

		printProbabilityRecursively(n);

		System.out.println("");

		printProbability(n);

		scanner.close();
	}

	/**
	 * 打印出色子点数总数的概率
	 * 
	 * @param n
	 *            色子个数
	 */
	public static void printProbabilityRecursively(int n) {
		if (n < 1)
			return;
		// 最大的点数
		int maxSum = n * MAX_VALUE;
		// 用一个数组来存取每个点数出现的次数
		int[] probabilities = new int[maxSum - n + 1];

		probabilityRecursively(probabilities, n);

		// 总共出现的情况数，全排列
		double total = Math.pow(MAX_VALUE, n);

		System.out.println("-----------" + n + "个色子的点数总数概率情况:" + "-----------");
		for (int i = n; i <= maxSum; i++)
			System.out.println(i + "出现的概率: " + String.format("%.4f", probabilities[i - n] / total));
	}

	/**
	 * 求色子某个总数出现的次数
	 * 
	 * @param probabilities
	 *            用来存放各个点数之和的数组
	 * @param n
	 *            色子个数
	 */
	public static void probabilityRecursively(int[] probabilities, int n) {
		for (int i = 1; i <= MAX_VALUE; i++)
			probabilityRecursively(n, n, i, probabilities);
	}

	/**
	 * 使用递归求色子每种总数出现的次数
	 * 
	 * @param base
	 *            色子点数之和的最小值，也是色子之和的基点
	 * @param current
	 *            当前使用的色子数
	 * @param sum
	 *            色子点数之和
	 * @param probabilities
	 *            用来存放各个点数之和的数组
	 */
	public static void probabilityRecursively(int base, int current, int sum, int[] probabilities) {
		if (current == 1)
			probabilities[sum - base]++;
		else
			for (int i = 1; i <= MAX_VALUE; i++)
				probabilityRecursively(base, current - 1, sum + i, probabilities);
	}

	/**
	 * 打印出色子点数总数的概率
	 * 
	 * @param n
	 *            色子个数
	 */
	public static void printProbability(int n) {
		if (n < 1)
			return;
		// 定义一个二维数组来存储每种总数出现的情况
		int[][] probabilities = new int[2][MAX_VALUE * n + 1];
		// 区分两个数组的标志
		int flag = 0;
		// 当只有1个色子时，第1个数组中第1-6位都应该为1
		for (int i = 1; i <= MAX_VALUE; i++)
			probabilities[flag][i] = 1;

		// 当有2-n个色子时
		for (int k = 2; k <= n; k++) {
			// 当有k个色子时，色子点数之和最小为k
			// 所以需要将k之前的元素都置为0
			for (int m = 0; m < k; m++)
				probabilities[1 - flag][m] = 0;
			// 填充另一个存储色子点数之和数组的值
			for (int h = k; h <= k * MAX_VALUE; h++) {
				// 先清空置为0
				probabilities[1 - flag][h] = 0;
				// 数组[k]等于另一个数组[h-1]+[h-2]+[h-3]+[h-4]+[h-5]+……+[h-MAX_VALUE]
				// 但同时需要注意k-j应该大于0
				for (int j = 1; j <= MAX_VALUE && j <= h; j++)
					probabilities[1 - flag][h] += probabilities[flag][h - j];
			}
			// 交换数组
			flag = 1 - flag;
		}

		// 总共出现的情况数，全排列
		double total = Math.pow(MAX_VALUE, n);
		System.out.println("-----------" + n + "个色子的点数总数概率情况:" + "-----------");
		for (int i = n; i <= MAX_VALUE * n; i++)
			System.out.println(i + "出现的概率: " + String.format("%.4f", probabilities[flag][i] / total));
	}

}
