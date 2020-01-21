package leetcode;

/**
    Given a string, find the length of the longest substring without repeating characters.

    Example 1:

        Input: "abcabcbb"
        Output: 3
        Explanation: The answer is "abc", with the length of 3.

    Example 2:

        Input: "bbbbb"
        Output: 1
        Explanation: The answer is "b", with the length of 1.

    Example 3:

        Input: "pwwkew"
        Output: 3
        Explanation: The answer is "wke", with the length of 3.
            Note that the answer must be a substring, "pwke" is a subsequence and not a substring.


    date: 2020-01-20
 */


import java.util.*;

@SuppressWarnings("unused")
public class Q0003_LongestSubstringWithoutRepeatingCharacters {

    /**
        算法一：自己解法

        首先定义一个list集合和一个int类型变量max（用于存储最大长度），将字符串转成char[]数组。
        遍历该数组，如果list中没有该字符，就将其放入list中，并更新max的值；如果list中含有该字符，就按照该字符将list分成两部分，
        第一部分用于和max比较并更新max，第二部分用于继续遍历拼接char[]数组中剩余的字符串

     */
    public int lengthOfLongestSubstring_1(String s) {
        char[] chars = s.toCharArray();
        int max = 0;
        List<Character> list = new ArrayList<>();
        for (char c : chars) {
            if (!list.contains(c)) {
                list.add(c);
                if (list.size() > max) {
                    max = list.size();
                }
            } else {
                int index = list.indexOf(c);
                if (index > max) {
                    max = index;
                }
                list = new ArrayList<>(list.subList(index + 1, list.size()));
                list.add(c);
            }
        }
        if (list.size() > max) {
            max = list.size();
        }
        return max;
    }



    /**
        解法二：滑动窗口

        暴力法非常简单，但它太慢了。那么我们该如何优化它呢？

        在暴力法中，我们会反复检查一个子字符串是否含有有重复的字符，但这是没有必要的。如果从索引 i 到 j - 1 之间的子字符串 s_{ij}已
        经被检查为没有重复字符。我们只需要检查 s[j] 对应的字符是否已经存在于子字符串 s_{ij}中。

        要检查一个字符是否已经在子字符串中，我们可以检查整个子字符串，这将产生一个复杂度为 O(n^2) 的算法，但我们可以做得更好。

        通过使用 HashSet 作为滑动窗口，我们可以用 O(1) 的时间来完成对字符是否在当前的子字符串中的检查。

        滑动窗口是数组/字符串问题中常用的抽象概念。 窗口通常是在数组/字符串中由开始和结束索引定义的一系列元素的集合，即 [i, j)（左
        闭，右开）。而滑动窗口是可以将两个边界向某一方向“滑动”的窗口。例如，我们将 [i, j) 向右滑动 1 个元素，则它将变为 [i+1, j+1)
        （左闭，右开）。

        回到我们的问题，我们使用 HashSet 将字符存储在当前窗口 [i, j)（最初 j = i）中。 然后我们向右侧滑动索引 j，如果它不在
        HashSet 中，我们会继续滑动 j。直到 s[j] 已经存在于 HashSet 中。此时，我们找到的没有重复字符的最长子字符串将会以索引 i 开
        头。如果我们对所有的 i 这样做，就可以得到答案。

     */
    public int lengthOfLongestSubstring_2(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int max = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                max = Math.max(max, j - i);
            }
            else {
                set.remove(s.charAt(i++));
            }
        }
        return max;
    }


    /**
        解法三：优化的滑动窗口

        上述的方法最多需要执行 2n 个步骤。事实上，它可以被进一步优化为仅需要 n 个步骤。我们可以定义字符到索引的映射，而不是使用集合
        来判断一个字符是否存在。 当我们找到重复的字符时，我们可以立即跳过该窗口。

        也就是说，如果 s[j] 在 [i, j) 范围内有与 j' 重复的字符，我们不需要逐渐增加 i 。 我们可以直接跳过 [i，j'] 范围内的所有元素，
        并将 i 变为 j' + 1
     */
    public int lengthOfLongestSubstring_3(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }


    /**
        解法四：使用字符集来替换Map

        以前的我们都没有对字符串 s 所使用的字符集进行假设。

        当我们知道该字符集比较小的时侯，我们可以用一个整数数组作为直接访问表来替换 Map。

        常用的表如下所示：

         int [26] 用于字母 ‘a’ - ‘z’ 或 ‘A’ - ‘Z’
         int [128] 用于ASCII码
         int [256] 用于扩展ASCII码


         复杂度分析:

         时间复杂度：O(n)，索引 jj 将会迭代 n 次。

         空间复杂度（HashMap）：O(min(m, n))，与之前的方法相同。

         空间复杂度（Table）：O(m)，m 是字符集的大小。
     */
    public int lengthOfLongestSubstring_4(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }
}
