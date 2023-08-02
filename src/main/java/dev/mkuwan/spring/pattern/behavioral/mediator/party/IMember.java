package dev.mkuwan.spring.pattern.behavioral.mediator.party;

public interface IMember {
    void joinedParty(IParty party);
    void receiveMessage(IMember member, String message);
    void say(String message);

    String getName();
}
