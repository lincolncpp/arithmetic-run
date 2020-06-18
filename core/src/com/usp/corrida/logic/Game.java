package com.usp.corrida.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.usp.corrida.Core;
import com.usp.corrida.utils.Utils;

/**
 * Classe responsável pela renderização do cenário
 */
public class Game {

    public static final float NPC_SPEED = 1.3f;

    // Core instance
    Core core;

    // Texture Pack
    Texture texBackground;
    Texture texTerrain;
    Texture texTree;

    // Texture regions
    TextureRegion texCloud;
    TextureRegion texBgCloud;
    TextureRegion texBgMountain;
    TextureRegion texBgTop;

    TextureRegion texTile1;
    TextureRegion texTile2;

    // Clouds
    public static final int MAX_CLOUD = 5;
    float[] cloudPositionX = new float[MAX_CLOUD];
    float[] cloudPositionY = new float[MAX_CLOUD];

    // Trees
    public static final int MAX_TREE = 10;
    float[] treePositionX = new float[MAX_TREE];

    // NPCs
    public static final int MAX_NPC = 3;
    public Character[] npc = new Character[MAX_NPC];
    float lastNPCX = 0;

    // Character
    public Character charMain;

    // Game variables
    public int life = 3;

    /**
     * Carrega recursos gráficos do jogo
     */
    public void loadResources(){
        texBackground = new Texture(Gdx.files.internal("background.png"));
        texTerrain = new Texture(Gdx.files.internal("terrain.png"));
        texTree = new Texture(Gdx.files.internal("tree.png"));

        texCloud = new TextureRegion(texBackground, 0, 16, 16, 16);
        texBgCloud = new TextureRegion(texBackground, 16, 0, 160, 32);
        texBgMountain = new TextureRegion(texBackground, 16, 32, 160, 48);
        texBgTop = new TextureRegion(texBackground, 0, 0, 16, 16);

        texTile1 = new TextureRegion(texTerrain, 16, 0, 16, 16);
        texTile2 = new TextureRegion(texTerrain, 16, 16, 16, 16);
    }

    /**
     * Define a posição inicial das nuvens
     */
    public void setupClouds(){
        for(int i = 0; i < MAX_CLOUD; i++){
            cloudPositionX[i] = core.rand.getIntRand(-16, (int)core.width);
            cloudPositionY[i] = core.rand.getIntRand(100,(int)core.height-64);
        }
    }

    /**
     * Define a posição incial das árvores
     */
    public void setupTrees(){
        for(int i = 0;i < MAX_TREE;i++){
            treePositionX[i] = core.rand.getIntRand(-48, (int)core.width*2);
        }
    }

    /**
     * Configuração inicial do jogador
     */
    public void setupPlayer(){
        charMain = new Character(core, 0);
        charMain.setHorizontalFlip(true);
        charMain.setPos(32, 32);
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
     * Atualiza os NPCs no cenário
     * @param offsetX Deslocamento da coordenada x do cenário
     */
    public void updateNPCs(float offsetX){
        for(int i = 0;i < MAX_NPC;i++) {
            float realX = npc[i].getX()-offsetX*NPC_SPEED;
            if (realX < -100){
                int sid = core.rand.getIntRand(1, 9);

                npc[i].setSprite(sid);
                if (sid == 7) npc[i].setY(core.rand.getIntRand(32, (int)core.height-100));
                else npc[i].setY(32);

                float x1 = offsetX*NPC_SPEED + core.width + core.rand.getIntRand(0, (int)(core.width/2f));
                float x2 = lastNPCX + core.rand.getIntRand(100, (int)(core.width/2f));
                float newX = Math.max(x1, x2);
                lastNPCX = newX;

                npc[i].setX(newX);
            }
        }
    }

    /**
     * @param core Instancia do core do jogo
     */
    public Game(Core core){
        this.core = core;

        loadResources();
        setupClouds();
        setupTrees();
        setupPlayer();
        setupNPCs();
    }

    /**
     * Essa função é chamada antes da função render. É utilizada para atualizar tudo antes da renderização
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param offsetX Deslocamento da coordenada x do cenário
     */
    public void update(float delta, float offsetX){
        // Updating tree position
        for(int i = 0;i < MAX_TREE;i++){
            float posx = treePositionX[i]-offsetX;
            if (posx < -48) treePositionX[i] = offsetX+core.width+core.rand.getIntRand(0, (int)core.width);
        }

        updateNPCs(offsetX);
    }

    /**
     * Renderiza todo o cenário do jogo
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param offsetX Deslocamento da coordenada x do cenário
     */
    public void render(float delta, float offsetX){
        update(delta, offsetX);

        // Drawing top effect
        int cnt1 = ((int)core.width-1)/16+1;
        for(int i = 0;i < cnt1;i++){
            core.batch.draw(texBgTop, 16*i, core.height-48);
        }
        core.batch.draw(core.res.texBlack, 0, core.height-32, core.width, 32);

        // Drawing background clouds
        int cnt3 = ((int)core.width-1)/160+1;
        float offset3 = Utils.fixFloat((offsetX/4f)%160);
        for(int i = 0;i < cnt3+1;i++){
            core.batch.draw(texBgCloud, 160*i - offset3, 64);
        }

        // Drawing mountain
        int cnt2 = ((int)core.width-1)/160+1;
        float offset2 = Utils.fixFloat((offsetX/2f)%160);

        for(int i = 0;i < cnt2+1;i++){
            core.batch.draw(texBgMountain, 160*i - offset2, 16);
        }

        // Drawing terrain
        int cnt4 = ((int)core.width-1)/16+1;
        float offset4 = Utils.fixFloat(offsetX%16);
        for(int i = 0;i < cnt4+1;i++){
            core.batch.draw(texTile1, 16*i-offset4, 16);
            core.batch.draw(texTile2, 16*i-offset4, 0);
        }

        // Drawing clouds
        for(int i = 0; i < MAX_CLOUD; i++){
            core.batch.draw(texCloud, cloudPositionX[i], cloudPositionY[i]);
        }

        // Drawing trees
        for(int i = 0;i < MAX_TREE;i++){
            core.batch.draw(texTree, treePositionX[i]-offsetX, 32);
        }

        // Drawing NPCs
        for(int i = 0;i < MAX_NPC;i++){
            npc[i].render(delta, offsetX*NPC_SPEED);
        }

        charMain.render(delta, 0);
    }

    /**
     * Descarrega todos os recursos
     */
    public void dispose(){
        texBackground.dispose();
        texTerrain.dispose();
        texTree.dispose();
    }
}
