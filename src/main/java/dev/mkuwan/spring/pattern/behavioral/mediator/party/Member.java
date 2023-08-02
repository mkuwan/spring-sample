package dev.mkuwan.spring.pattern.behavioral.mediator.party;

public class Member implements IMember{

    private IParty party;



    private final String name;

    public Member(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public void joinedParty(IParty party) {
        this.party = party;
    }

    @Override
    public void receiveMessage(IMember member, String message) {
        System.out.println(member.getName() + "からのメッセージです:=" + message);
    }

    @Override
    public void say(String message) {
        if (party != null){
            party.sendMessage(this, message);
        }
    }
}
