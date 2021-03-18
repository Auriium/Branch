package me.aurium.beetle.branch.tests.mockups;

import me.aurium.beetle.branch.CommandNode;
import me.aurium.beetle.branch.adapter.ContextAdapterFactory;
import me.aurium.beetle.branch.builders.Base;
import me.aurium.beetle.branch.builders.BranchingBuilder;
import me.aurium.beetle.branch.builders.SingleBuilder;
import me.aurium.beetle.branch.launchpoints.AbstractBranchCommand;
import org.junit.platform.commons.logging.Logger;

/**
 * A fake command pretending to be a kitpvp plugin command
 */
public class BranchingCommand extends AbstractBranchCommand<String> {
    private final Logger logger;

    public BranchingCommand(ContextAdapterFactory<String> factory, Logger logger) {
        super(factory);

        this.logger = logger;
    }

    @Override
    public CommandNode<String> getBaseNode() {
        return Base.base(new BranchingBuilder<>(),kitpvp -> {

            //argument /kitpvp join, which always prints that they joined the match
            kitpvp.withNode(new SingleBuilder<>(), join -> {
                join.withIdentifier("join");
                join.withHandler(handler -> {
                    logger.info(() -> handler.getSender() + " joined the kitpvp match!");
                    //logic
                });
            });

            //argument /kitpvp invites <on/off>
            kitpvp.withNode(new BranchingBuilder<>(), invites -> {
                invites.withIdentifier("invites");
                invites.withNode(new SingleBuilder<>(), on -> {
                    on.withIdentifier("on");
                    on.withHandler(text -> {
                        logger.info(() -> "Invites toggled on!");
                    });

                });
                invites.withNode(new SingleBuilder<>(), off -> {
                    off.withIdentifier("on");
                    off.withHandler(text -> {
                        logger.info(() -> "Invites toggled on!");
                    });
                });
                invites.withNoArgs(new SingleBuilder<>(), false, noArgs -> {
                    noArgs.withIdentifier("unlinked");
                    noArgs.withHandler(handler -> {
                        logger.info(() -> "You must specify either on or off!");
                    });
                });
            });

            //argument /kitpvp help, which is the default
            kitpvp.withNoArgs(new SingleBuilder<>(), true, help -> {
                help.withIdentifier("help");
                help.withHandler(handler -> {
                    logger.info(() -> "Hey, " + handler.getSender() + ", here is some help from KitPVP!");
                    logger.info(() -> "/kitpvp help");
                    logger.info(() -> "/kitpvp join");
                    logger.info(() -> "/kitpvp invites <on/off>");
                });
            });


        });
    }

    @Override
    public String getName() {
        return "kitpvp";
    }

    @Override
    public String getPermission() {
        return "testPermission";
    }
}
