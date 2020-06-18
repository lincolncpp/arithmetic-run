package com.usp.corrida.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.usp.corrida.Core;
import com.usp.corrida.logic.Background;
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

    // Screen offset
    float offsetX = 0;

    // NPCs
    public static final int MAX_NPC = 3;
    Character[] npc = new Character[MAX_NPC];
    float lastNPCX = 0;

    // Game variables
    int life = 3;
    int points = 0;

    /**
     * @param core Instancia do core do jogo
     */
    public GameScreen(Core core){
        this.core = core;

        texLife = new Texture(Gdx.files.internal("life.png"));

        setupNPCs();
    }

    /**
     * Configuração inicial dos NPCs
     */
    public void setupNPCs(){
        for(int i = 0;i < MAX_NPC;i++) {
            npc[i] = new Character(core, 0);
            npc[i].setIsMoving(true);
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


                for(int i = 0; i < MAX_NPC; i++){
                    if (npc[i].isTouched((int)fixedCoordinate.x, (int)fixedCoordinate.y, offsetX)){
                        System.out.println("npc " + i + " touched");
                    }
                }


                return true;
            }
        });

        // Set character moving
        core.charPlayer.setIsMoving(true);
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

                npc[i].setText(""+(i+1));
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
            npc[i].setX(npc[i].getX()-d);
        }

        updateNPCs(offsetX);
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
        core.res.font.draw(core.batch, "PONTOS: "+points, 10+20*2+16+10, core.height-32/2f+core.res.font.getCapHeight()/2f);

        // Drawing NPCs
        for(int i = 0;i < MAX_NPC;i++){
            npc[i].render(delta, offsetX);
        }

        core.charPlayer.render(delta, 0);
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
