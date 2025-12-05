package character.type;

public enum MotionType {
    // Man & Enemy 공통 모션
    IDLE, DEAD, DAMAGE, RUN,

    // Man 공격 모션
    MAN_ATTACK01, MAN_ATTACK02, MAN_ATTACK03, MAN_ATTACK04,

    // Man(Sword) 모션
    MAN_SWORD_RUN, MAN_SWORD_IDLE, MAN_SWORD_ATTACK01, MAN_SWORD_ATTACK02, MAN_SWORD_ATTACK03, MAN_SWORD_ATTACK04,

    // Enemy 공격 모션
    ENEMY_ATTACK01,

    // Enemy 스킬 모션
    ENEMY_SKILL01;
}
