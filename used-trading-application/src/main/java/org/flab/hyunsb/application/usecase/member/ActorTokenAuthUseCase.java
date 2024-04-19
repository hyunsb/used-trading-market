package org.flab.hyunsb.application.usecase.member;

import org.flab.hyunsb.application.dto.Actor;

public interface ActorTokenAuthUseCase {

    Actor authenticate(String actorToken);
}
