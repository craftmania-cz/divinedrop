package com.demkom58.divinedrop.version.V13R1;

import com.demkom58.divinedrop.DivineDrop;
import com.demkom58.divinedrop.config.ConfigData;
import com.demkom58.divinedrop.drop.ItemHandler;
import com.demkom58.divinedrop.lang.Language;
import com.demkom58.divinedrop.version.V11R1.V11R1;
import com.demkom58.divinedrop.version.V12R1.V12Listener;
import com.demkom58.divinedrop.version.Version;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class V13R1 implements Version {
    public static final String VERSION = "1.13";
    public static final String PATH = "minecraft/lang/%s.json";

    private final DivineDrop plugin;
    private final ConfigData data;
    private final ItemHandler itemHandler;

    private V13R1() {
        this.plugin = null;
        this.data = null;
        this.itemHandler = null;
    }

    public V13R1(@NotNull final DivineDrop plugin,
                 @NotNull final ConfigData data,
                 @NotNull final ItemHandler itemHandler) {
        this.plugin = plugin;
        this.data = data;
        this.itemHandler = itemHandler;
    }

    @Override
    public String getI18NDisplayName(@Nullable ItemStack item) {
        if (item == null)
            return null;

        return getName(item);
    }

    @NotNull
    @Override
    public String getLangPath(@NotNull final String locale) {
        return String.format(PATH, locale.toLowerCase());
    }

    @NotNull
    @Override
    public Map<String, String> parseLang(@NotNull InputStream inputStream) throws IOException {
        return V13LangParser.parseLang(inputStream);
    }

    @NotNull
    @Override
    public String id() {
        return VERSION;
    }

    @NotNull
    @Override
    public Listener createListener() {
        return new V12Listener(itemHandler);
    }

    @Override
    public @NotNull String reformatLangCode(@NotNull final String localeCode) {
        return V11R1.langCode(localeCode);
    }

    @NotNull
    private String getName(ItemStack bItemStack) {
        if (bItemStack.hasItemMeta()) {
            final ItemMeta itemMeta = bItemStack.getItemMeta();
            if (itemMeta.hasDisplayName())
                return itemMeta.getDisplayName();
        }

        return getLangNameNMS(org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack.asNMSCopy(bItemStack));
    }

    private String getLangNameNMS(net.minecraft.server.v1_13_R1.ItemStack itemStack) {
        return Language.getInstance().getLocName(itemStack.getItem().getName()).trim();
    }

}
