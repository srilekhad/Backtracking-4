//Time Complexity: O(k^n), or O(k^ (N/k)) where n is the number of groups, k is the max group size and N is the total length of the string
//Space Complexity: O(k^n) for storing all combinations generated.

// Parse the string left-to-right, collecting a group of characters: either a single literal or a set inside '{...}' (ignoring commas).
// Sort each group’s characters, then do a running Cartesian product: for every existing partial word, append each character in the current group to build new partials.
// After processing all groups, return the accumulated list as a String[].

class Solution {
    public String[] expand(String s) {
        int i=0;
        List<String> expandedWords = new ArrayList<>();
        expandedWords.add("");

        while(i < s.length()){
            List<Character> group = new ArrayList<>();
            if(s.charAt(i) == '{'){
                i++;
                while(s.charAt(i) != '}'){
                    if(s.charAt(i) != ','){
                        group.add(s.charAt(i));
                    }
                    i++;
                }
                i++;
            }else{
                group.add(s.charAt(i));
                i++;
            }
            
            Collections.sort(group);
            List<String> currWords = new ArrayList<>();

            for(String word: expandedWords){
                for(Character c: group){
                    currWords.add(word + c);
                }
            }
            expandedWords = currWords;
        }
        return expandedWords.toArray(new String[0]);
    }
}
