package dev.mkuwan.spring.pattern.behavioral.mediator.party;

public interface IParty {
    void addMember(IMember member);
    void sendMessage(IMember actor, String message);
}
