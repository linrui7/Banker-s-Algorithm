package com.Banker;
/*
    Name:  linrui
    Date: 2019/5/8
*/

import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

    //进程最大资源
    int max[][] = new int[][]{
            {7, 5, 3},
            {3, 2, 2},
            {9, 0, 2},
            {2, 2, 2},
            {4, 3, 3}};
    //允许分配的
    int allocation[][] = new int[][]{
            {0, 1, 0},
            {2, 0, 0},
            {3, 0, 2},
            {2, 1, 1},
            {0, 0, 2}};
    //还需要的资源
    int need[][] = new int[][]{
            {7, 4, 3},
            {1, 2, 2},
            {6, 0, 0},
            {0, 1, 1},
            {4, 3, 1}};
    //系统当前剩余资源
    int available[] = new int[]{3, 3, 2};
    //保存安全序列
    ArrayList<Integer> arrayList = new ArrayList<Integer>();

    //输出当前表格
    public void showData() {
        System.out.println("当前五个进程如下");
        //进程最大资源
        System.out.println("   Max");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + max[i][j] + "\t");
            }
            System.out.println();
        }
        //还需要的资源
        System.out.print("Allocation:\n");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + allocation[i][j] + "\t");
            }
            System.out.println();
        }
        //系统当前剩余资源
        System.out.println("Available");
        for (int i = 0; i < available.length; i++) {
            System.out.print(" " + available[i] + "\t");
        }
    }

    //分配数据
    public boolean isSafe(int req, int reqnum[]) {
        int requestNum = req;
        int request[] = reqnum;
        //判断申请的资源是否大于需求值
        if (!(request[0] <= need[requestNum][0]
                && request[1] <= need[requestNum][1]
                && request[2] <= need[requestNum][2])) {
            System.out.println("申请资源大于需求");
            return false;
        }
        //判断当前资源是否够用
        if (( request[0] <= available[0]
           && request[1] <= available[1]
           && request[2] <= available[2]) == false) {
            System.out.println("无足够资源分配");
            return false;
        }
        //分配资源
        for (int i = 0; i < 3; i++) {
            available[i] = available[i] - request[i];
            allocation[requestNum][i] = allocation[requestNum][i] + request[i];
            need[requestNum][i] = need[requestNum][i] - request[i];
        }
        //检查是否安全
        boolean flag = checkSafe(available[0], available[1], available[2]);
        if ((flag) == true) {
            System.out.println(arrayList);
            return true;
        } else {
            System.out.println("未找到安全序列");
            return false;
        }
    }

    public boolean checkSafe(int a, int b, int c) {
        //定义当前系统可分配资源
        arrayList.clear();
        int work[] = new int[]{a, b, c};
        //保存五个进程，是否可以执行
        boolean finish[] = new boolean[5];
        //找到一个Need小于Work+Allocation的进程
        int i = 0;
        while (i < 5) {
            if (finish[i] == false
                    && need[i][0] <= work[0]
                    && need[i][1] <= work[1]
                    && need[i][2] <= work[2]) {
                arrayList.add(i);
                //运行完之后，可用资源再加上Allocation
                for (int y = 0; y < 3; y++) {
                    work[y] = work[y] + allocation[i][y];
                }
                //当前可用
                finish[i] = true;
                i = 0;
                //如果没有找到可分配的，继续下一个
            } else {
                i++;
            }
        }
        //当前五个进程以及都过了一遍，判断是否已经运行
        for (i = 0; i < 5; i++) {
            if (finish[i] == false) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.showData();
        int request[] = new int[3];
        int requestNum;
        Scanner scanner;
        //输入当前的进程以及请求的资源向量
        while (true) {
            scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("输入要发出请求的进程\n0 , 1 , 2 , 3 , 4");
            requestNum = scanner.nextInt();
            System.out.println("输入请求资源的向量");
            for (int i = 0; i < 3; i++) {
                request[i] = scanner.nextInt();
            }
            bank.isSafe(requestNum, request);
        }
    }
}
