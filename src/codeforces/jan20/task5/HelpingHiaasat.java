package codeforces.jan20.task5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class HelpingHiaasat {

    private static Map<String, Set<String>> friendToCompeting = new HashMap<>();
    private static Map<String, Set<String>> friendToCompetingUniquely = new HashMap<>();
    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int eventCount = sysIn.nextInt();
        int friendCount = sysIn.nextInt();
        sysIn.nextLine();

        Set<String> competingCurrently = new HashSet<>();
        for ( int i = 0; i < eventCount; i++ ) {
            String line = sysIn.nextLine();
            if ( line.charAt(0) == '2' ) {
                competingCurrently.add(line);
            } else {
                updateConflicts(competingCurrently);
                competingCurrently.clear();
            }
        }
        updateConflicts(competingCurrently);
        competingCurrently.clear();

        for ( String key : friendToCompeting.keySet() ){
            friendToCompeting.get(key).remove(key);
        }
        for ( String key : friendToCompetingUniquely.keySet() ){
            friendToCompetingUniquely.get(key).remove(key);
        }

        Set<String> empty = new HashSet<>();
        while ( friendToCompeting.size() != 0 || friendToCompetingUniquely.size() != 0 ){
            friendCount--;

            String worstConflictFriend = null;
            int worstConflictCount = 0;
            int worstUniqueConflictCount = 0;
            if ( friendToCompetingUniquely.size() != 0 ){

                for ( Map.Entry<String, Set<String>> conflict : friendToCompetingUniquely.entrySet() ){
                    int conflictCount = friendToCompeting.getOrDefault(conflict, empty).size();
                    if ( worstUniqueConflictCount < conflict.getValue().size() ||
                            (worstUniqueConflictCount == conflict.getValue().size() &&
                                    worstConflictCount < conflictCount)){
                        worstConflictFriend = conflict.getKey();
                        worstConflictCount = conflictCount;
                        worstUniqueConflictCount = conflict.getValue().size();
                    }
                }

                Set<String> conflicts = friendToCompetingUniquely.remove(worstConflictFriend);
                Set<String> tmp;
                //Update unique conflicts
                for ( String friend : conflicts ){
                    tmp = friendToCompetingUniquely.get(friend);
                    tmp.remove(worstConflictFriend);
                    if ( tmp.isEmpty() ){
                        friendToCompetingUniquely.remove(friend);
                    }
                }

                //Update conflicts
                conflicts = friendToCompeting.remove(worstConflictFriend);
                if ( conflicts == null ) continue;
                for ( String friend : conflicts ){
                    tmp = friendToCompeting.get(friend);
                    tmp.remove(worstConflictFriend);
                    if ( tmp.size() < 2 ){
                        friendToCompeting.remove(friend);
                        if ( tmp.size() == 1 ){
                            friendToCompetingUniquely.merge(friend, tmp, (s1, s2) -> {
                                s1.addAll(s2);
                                return s1;
                            });
                        }
                    }
                }
            } else {
                for ( Map.Entry<String, Set<String>> conflict : friendToCompeting.entrySet() ){
                    if ( worstConflictCount < conflict.getValue().size() ){
                        worstConflictFriend = conflict.getKey();
                        worstConflictCount = conflict.getValue().size();
                    }
                }

                Set<String> conflicts = friendToCompeting.remove(worstConflictFriend);
                Set<String> tmp;
                for ( String friend : conflicts ){
                    tmp = friendToCompeting.get(friend);
                    tmp.remove(worstConflictFriend);
                    if ( tmp.isEmpty() ){
                        friendToCompeting.remove(friend);
                    }
                }
            }

        }
        System.out.println(friendCount);

    }
    private static void updateConflicts(Set<String> competingCurrently){
        for ( String friend : competingCurrently ) {
            if ( competingCurrently.size() == 1 ){
                return;
            } else if ( competingCurrently.size() == 2 ) {
                friendToCompetingUniquely.putIfAbsent(friend, new HashSet<>());
                friendToCompetingUniquely.get(friend).addAll(competingCurrently);
            } else {
                friendToCompeting.putIfAbsent(friend, new HashSet<>());
                friendToCompeting.get(friend).addAll(competingCurrently);
            }
        }
    }
}
