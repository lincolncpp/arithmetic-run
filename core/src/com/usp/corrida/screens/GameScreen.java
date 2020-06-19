package com.usp.corrida.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.usp.corrida.Core;
import com.usp.corrida.logic.Character;
import com.usp.corrida.utils.Utils;

/**
 * Classe destinada à renderização da tela de jogo
 */
public class GameScreen extends ScreenAdapter {

    // Core instance
    Core core;

    // Texture
    Texture texLife;

    // Tick variables
    public static final long POINTS_EFFECT_INTERVAL = 65;
    public static final long HURT_INTERVAL = 1500;
    public static final long GAMEOVER_SLIDE = 1000;

    long tickPoints = 0;
    long tickHurt = 0;
    long tickGameOver = 0;

    // Screen offset
    float offsetX = 0;

    // NPCs
    public static final int MAX_NPC = 3;
    Character[] npc = new Character[MAX_NPC];
    float lastNPCX = 0;

    // Game variables
    int life = 3;
    long scoreAdd = 0;
    long score = 0;
    int challengeValue = 0;
    Boolean gameOver = false;
    String gameOverMessage = "";

    /**
     * @param core Instancia do core do jogo
     */
    public GameScreen(Core core){
        this.core = core;

        texLife = new Texture(Gdx.files.internal("life.png"));

        resetScreen();
    }

    /**
     * @return nível de dificuldade atual com base na pontuação
     */
    public int getLevel(){
//        if (score >= 10000000) return 4;
//        if (score >= 100000) return 3;
//        if (score >= 1000) return 4;
        return 1;
    }

    /**
     * Atualiza o desafio
     */
    public void refreshChallenge(){
        int level = getLevel();
        if (level == 1) challengeValue = core.rand.getIntRand(11, 100);
        else if (level == 2) challengeValue = core.rand.getIntRand(101, 1000);
        else if (level == 3) challengeValue = core.rand.getIntRand(1001, 10000);
        else if (level == 4) challengeValue = core.rand.getIntRand(10001, 100000);

        core.charPlayer.setText(""+challengeValue);
    }

    /**
     * Atualiza a resposta do NPC i
     */
    public void updateNPCAnswer(int i){
        int level = getLevel();
        if (level == 1){
            // Only + and -
            int operation = core.rand.getIntRand(0, 1);
            int correct = core.rand.getIntRand(1, 10);

            // 60% to pick a correct answer
            if (correct <= 6) npc[i].setValue(challengeValue);
            else {
                npc[i].setValue(core.rand.getIntRand(2, 100));
                while(npc[i].getValue() == challengeValue) npc[i].setValue(core.rand.getIntRand(2, 100));
            }

            if (operation == 1){ // + operation
                int a = core.rand.getIntRand(1, Math.min(10, npc[i].getValue()-1));
                npc[i].setText(a+"+"+(npc[i].getValue()-a));
            }
            else{ // - operation
                int a = core.rand.getIntRand(1, 10);
                npc[i].setText((npc[i].getValue()+a)+"-"+a);
            }
        }
    }

    /**
     * Reseta os componentes da tela
     */
    public void resetScreen(){
        tickPoints = 0;
        tickHurt = 0;
        tickGameOver = 0;

        offsetX = 0;
        lastNPCX = 0;

        life = 3;
        scoreAdd = 0;
        score = 0;
        challengeValue = 0;
        gameOver = false;
        gameOverMessage = "";

        core.charPlayer.setIsMoving(true);

        refreshChallenge();

        setupNPCs();
    }

    /**
     * Configuração inicial dos NPCs
     */
    public void setupNPCs(){
        for(int i = 0;i < MAX_NPC;i++) {
            npc[i] = new Character(core, 0);
            npc[i].setX(-10000);
        }
    }

    /**
     * Essa função é chamada quando a tela acaba de ser exibida
     */
    @Override
    public void show(){
        // Input Processor
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                Vector2 fixedCoordinate = Utils.fixTouchPosition(core, x, y);

                if (gameOver){
                    core.setScreen(core.titleScreen);
                }

                if (life > 0){
                    for(int i = 0; i < MAX_NPC; i++){
                        if (npc[i].getSprite() == 10) continue;

                        if (npc[i].isTouched((int)fixedCoordinate.x, (int)fixedCoordinate.y, offsetX)){
                            if (npc[i].getValue() == challengeValue){
                                scoreAdd += 100;
                                npc[i].setSprite(10);
                                npc[i].setText("");
                            }
                            else{
                                life--;
                                tickHurt = System.currentTimeMillis()+HURT_INTERVAL;

                                if (life == 0){
                                    core.charPlayer.setFrameInterval(60);
                                    core.charPlayer.setText("$%!#@");

                                    long totalScore = score+scoreAdd;
                                    if (totalScore > core.save.getHighScore()){
                                        core.save.setHighScore(totalScore);
                                        gameOverMessage = "NOVO RECORDE!";
                                    }
                                    else gameOverMessage = "RECORDE: "+core.save.getHighScore();

                                    for(int j = 0;j < MAX_NPC;j++) npc[j].setText("");
                                    break;
                                }
                            }
                        }
                    }
                }

                return true;
            }
        });

        resetScreen();
    }

    /**
     * Essa função é chamada quando a tela acaba de ser escondida
     */
    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Atualiza os NPCs no cenário
     * @param offsetX Deslocamento da coordenada x do cenário
     */
    public void updateNPCs(float offsetX){
        for(int i = 0;i < MAX_NPC;i++) {
            float realX = npc[i].getX()-offsetX;
            if (realX < -100){
                int sid = core.rand.getIntRand(1, 9);

                npc[i].setSprite(sid);
                if (sid == 7) npc[i].setY(core.rand.getIntRand(32, (int)core.height-100));
                else npc[i].setY(32);

                float x1 = offsetX + core.width + core.rand.getIntRand(0, (int)(core.width/2f));
                float x2 = lastNPCX + core.rand.getIntRand(100, (int)(core.width/2f));
                float newX = Math.max(x1, x2);
                lastNPCX = newX;

                npc[i].setX(newX);
                npc[i].setIsMoving(true);

                updateNPCAnswer(i);
            }

            // Smoke sprite
            if (npc[i].getSprite() == 10){
                if (npc[i].getFrame() == core.res.SPRITE_FRAMES[10]-1) npc[i].setX(-10000);
            }
        }
    }

    /**
     * Faz a animação do aumento de pontuação
     */
    public void updatePoints(){
        if (System.currentTimeMillis() > tickPoints){
            tickPoints = System.currentTimeMillis()+POINTS_EFFECT_INTERVAL;
            for(int i = 60;i >= 0;i--){
                if ((scoreAdd &(1L<<i)) > 0){
                    score += (1L<<i);
                    scoreAdd -= (1L<<i);
                    break;
                }
            }
        }
    }

    /**
     * Essa função é chamada antes da função render. É utilizada para atualizar tudo antes da renderização
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
    public void update(float delta){

        offsetX += delta*30;

        float d = delta*10;
        for(int i = 0; i < MAX_NPC; i++){
            if (npc[i].getSprite() == 10) continue;
            npc[i].setX(npc[i].getX()-d);
        }

        if (life == 0){
            float f = delta*150;
            core.charPlayer.setX(core.charPlayer.getX()+f);
        }

        updateNPCs(offsetX);
        updatePoints();
    }

    /**
     * Desenha um efeito na tela ao perder vida
     */
    public void renderHurt(){
        long time = tickHurt-System.currentTimeMillis();
        if (time > 0){

            float x = 3.8f-4f*time/(float)HURT_INTERVAL;
            core.batch.setColor(1, 1, 1, Math.min(1, (float) Math.exp(-x)));
            core.batch.draw(core.res.texHurt, 0, 0, core.width, core.height);
            core.batch.setColor(1, 1, 1, 1);
        }
    }

    /**
     * Desenha mensagem de fim de jogo
     */
    public void renderGameOver(){
        if (life == 0 && core.charPlayer.getX() > core.width){
            if (tickGameOver == 0) tickGameOver = System.currentTimeMillis();
            long time = System.currentTimeMillis()-tickGameOver;
            float x = time/(float)GAMEOVER_SLIDE*Utils.PI*Utils.PI;
            x = Math.min(x, Utils.PI*Utils.PI);
            float fx = -((float)Math.cos(Math.sqrt(x))+1)/2f*core.height;

            if (time > GAMEOVER_SLIDE) gameOver = true;

            core.batch.draw(core.res.texBlack, 0, fx, core.width, core.height-32);
            core.res.font32.draw(core.batch, "FIM DE JOGO!", 0, fx+core.height/2f+core.res.font32.getCapHeight()/2, core.width, 1, false);
            core.res.font20.draw(core.batch, gameOverMessage, 0, fx+core.height/2f+core.res.font32.getCapHeight()/2-25, core.width, 1, false);
        }
    }

    /**
     * Renderiza a tela de jogo
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
    @Override
    public void render (float delta) {
        update(delta);

        // Draw background
        core.background.render(delta, offsetX);

        // Drawing life
        for(int i = 0;i < 3;i++){
            core.batch.draw(texLife,10+20*i, core.height-32/2f-16/2f, (i+1>life)?16:0, 0, 16, 16);
        }

        // Drawing score
        core.res.font20.draw(core.batch, "PONTOS: "+ score, 10+20*2+16+10, core.height-32/2f+core.res.font20.getCapHeight()/2f);

        // Drawing characters
        for(int i = 0;i < MAX_NPC;i++){
            npc[i].render(delta, offsetX);
        }
        core.charPlayer.render(delta, 0);

        // Drawing hurt
        renderHurt();

        // Drawing game over
        renderGameOver();
    }

    /**
     * Descarrega todos os recursos
     */
    @Override
    public void dispose(){
        texLife.dispose();

        for(int i = 0;i < MAX_NPC;i++){
            npc[i].dispose();
        }
    }
}
