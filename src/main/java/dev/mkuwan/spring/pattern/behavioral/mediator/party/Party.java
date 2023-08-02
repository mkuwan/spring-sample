package dev.mkuwan.spring.pattern.behavioral.mediator.party;

import java.util.ArrayList;
import java.util.List;

public class Party implements IParty{

    private final List<IMember> members;

    public Party(){
        members = new ArrayList<>();
    }

    @Override
    public void addMember(IMember member) {
        members.add(member);
        member.joinedParty(this);
    }

    @Override
    public void sendMessage(IMember actor, String message) {
        for (var member : members) {
            if (!member.equals(actor)) {
                member.receiveMessage(actor, message);
            }
        }
    }
}
