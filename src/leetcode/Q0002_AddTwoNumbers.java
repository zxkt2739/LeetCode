package leetcode;
/**
    You are given two non-empty linked lists representing two non-negative integers.
    The digits are stored in reverse order and each of their nodes contain a single digit.
    Add the two numbers and return it as a linked list.

    You may assume the two numbers do not contain any leading zero, except the number 0 itself.

    Example:

        Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
        Output: 7 -> 0 -> 8
        Explanation: 342 + 465 = 807.


    date: 2019-08-16
 */


import utils.ListNode;

@SuppressWarnings("unused")
public class Q0002_AddTwoNumbers {

    /**
        算法分析

        就像你在纸上计算两个数字的和那样，我们首先从最低有效位也就是列表 l1 和 l2 的表头开始相加。由于每位数字都应当处于 0…9 的范围内，
        我们计算两个数字的和时可能会出现 “溢出”。例如，5 + 7 = 12。在这种情况下，我们会将当前位的数值设置为 22，并将进位 carry = 1
        带入下一次迭代。进位 carry 必定是 0 或 1，这是因为两个数字相加（考虑到进位）可能出现的最大和为 9 + 9 + 1 = 19。

        伪代码如下：

            * 将当前结点初始化为返回列表的哑结点。
            * 将进位 carry 初始化为 0。
            * 将 p 和 q 分别初始化为列表 l1 和 l2 的头部。
            * 遍历列表 l1 和 l2 直至到达它们的尾端。
                ^ 将 x 设为结点 p 的值。如果 p 已经到达 l1 的末尾，则将其值设置为 0。
                ^ 将 y 设为结点 q 的值。如果 q 已经到达 l2 的末尾，则将其值设置为 0。
                ^ 设定 sum = x + y + carry。
                ^ 更新进位的值，carry = sum / 10。
                ^ 创建一个数值为 (sum mod 10) 的新结点，并将其设置为当前结点的下一个结点，然后将当前结点前进到下一个结点。
                ^ 同时，将 p 和 q 前进到下一个结点。
            * 检查 carry = 1 是否成立，如果成立，则向返回列表追加一个含有数字 1 的新结点。
            * 返回哑结点的下一个结点。
        请注意，我们使用哑结点来简化代码。如果没有哑结点，则必须编写额外的条件语句来初始化表头的值。

        请特别注意以下情况：

            l1=[0,1]，l2=[0,1,2]	当一个列表比另一个列表长时
            l1=[]，l2=[0,1]	        当一个列表为空时，即出现空列表
            l1=[9,9]，l2=[1]	    求和运算最后可能出现额外的进位，这一点很容易被遗忘

     */

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 创建哑结点
        ListNode dummyHead = new ListNode(0);
        // 创建保存结果的循环变量，将哑结点赋为其初始值
        ListNode curr = dummyHead;
        // 创建l1和l2的循环变量
        ListNode p = l1;
        ListNode q = l2;
        // carry保存进位
        int carry = 0;
        while (p != null || q != null) {
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            // 移动循环变量到下一个结点
            curr = curr.next;
            p = p != null ? p.next : null;
            q = q != null ? q.next : null;
        }
        // 上面注意事项中第三种情况
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
}
