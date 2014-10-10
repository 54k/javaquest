package org.mozilla.browserquest.space;

public interface IAppSpaceCommand {

    IAppSpaceClient<?> getCommandSender();

    void execute();
}
